package org.challenger.challenger.domain;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.units.qual.C;

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
        usersChallengesRepository.linkUsersWithChallenge(ids, challengeId);
    }

    public Challenge createChallenge(Challenge challenge) {
        challengeRepository.create(challenge);
        log.info("createChallenge.exit; challengeId={}", challenge.getId());
        return challenge;
    }

    public Challenge getChallenge(String challengeId) {
        Challenge challenge = new Challenge();
        challenge.setState(ChallengeState.INITIAL);
        // TODO first to fix
        challenge = challengeRepository.getChallengeById(challengeId);
        challenge.setSubmissions(getSubmissionByChallengeId(challengeId));
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

    public void createSubmissionByChallengeId(Submission submission, String challengeId) {
        submissionRepository.createSubmissionByChallengeId(submission, challengeId);
        challengeRepository.updateChallengeProgress(submission.value(), challengeId);
    }

    public void updateChallengeState(String challengeId, ChallengeState challengeState) {
        challengeRepository.updateChallengeState(challengeId, challengeState);
    }
}
