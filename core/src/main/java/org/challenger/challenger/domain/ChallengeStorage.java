package org.challenger.challenger.domain;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@AllArgsConstructor
public class ChallengeStorage {

    private static final Integer DEFAULT_PROGRESS = 0;

    private final ChallengeRepository challengeRepository;
    private final UsersChallengesRepository usersChallengesRepository;
    private final SubmissionRepository submissionRepository;
    private final IdGenerator idGenerator;

    public Challenge createChallenge(String name, Integer goal, String userId, List<String> userIds, String chatId) {
        Challenge challenge = Challenge.builder()
            .id(idGenerator.generateId())
            .name(name)
            .goal(goal)
            .progress(DEFAULT_PROGRESS)
            .ownerUserId(userId)
            .userIds(userIds)
            .state(ChallengeState.INITIAL)
            .submissions(new ArrayList<>())
            .chatId(chatId)
            .build();
        return createChallenge(challenge);
    }

    public void linkUsersWithChallenge(List<String> ids, String challengeId) {
        usersChallengesRepository.linkUsersWithChallenge(challengeId, ids);
    }

    public List<String> getChallengeParticipants(String challengeId) {
        return usersChallengesRepository.getUsersIds(challengeId).stream()
            .map(UsersChallenges::userId)
            .toList();
    }

    public Challenge createChallenge(Challenge challenge) {
        challengeRepository.create(challenge);
        log.info("createChallenge.exit; challengeId={}", challenge.getId());
        return challenge;
    }

    public Challenge getChallenge(String challengeId) {
        Challenge challenge = challengeRepository.getChallengeById(challengeId)
            .orElseThrow(() -> new IllegalArgumentException("Challenge not found by id %s".formatted(challengeId)));
        challenge.setSubmissions(getSubmissionByChallengeId(challengeId));
        challenge.setUserIds(getChallengeParticipants(challengeId));
        return challenge;
    }

    public List<Challenge> getChallengesByChatId(String chatId) {
        return challengeRepository.getChallengesByChatId(chatId);
    }

    public Integer getChallengeProgress(String challengeId) {
        return challengeRepository.getChallengeProgress(challengeId);
    }

    public List<Submission> getSubmissionByChallengeId(String challengeId) {
        return submissionRepository.getSubmissionByChallengeId(challengeId);
    }

    public void submit(String challengeId, Submission submission) {
        Challenge challenge = this.getChallenge(challengeId);
        int newProgress = challenge.getProgress() + submission.value();
        boolean isChallengeFinished = newProgress >= challenge.getGoal();
        challengeRepository.updateChallengeProgress(challengeId, newProgress);
        submissionRepository.createSubmissionByChallengeId(challengeId, submission);
        if (isChallengeFinished) {
            log.info("Challenged marked as finished id = {}", challengeId);
            challengeRepository.updateChallengeState(challengeId, ChallengeState.FINISHED);
        }
    }

    public void updateChallengeState(String challengeId, ChallengeState challengeState) {
        challengeRepository.updateChallengeState(challengeId, challengeState);
    }

    public void activateChallenge(String challengeId) {
        challengeRepository.updateChallengeState(challengeId, ChallengeState.ACTIVATED);
    }
}
