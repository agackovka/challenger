package org.challenger.challenger.domain;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
public class ChallengeStorage {

    public static void main(String[] args) {
        IdGenerator idGenerator = () -> UUID.randomUUID().toString();

        ChallengeStorage challengeStorage = new ChallengeStorage(idGenerator);
//        List<String> r = new ArrayList<>();
//        r.add("2");
//        r.add("3");
//        Challenge challenge = Challenge.builder()
//            .id("3")
//            .progress(2)
//            .goal(2)
//            .ownerUserId("2")
//            .chatId("2")
//            .name("2")
//            .state(ChallengeState.INITIAL)
//            .type(ChallengeType.NUMERIC)
//            .userIds(r)
//            .build();
//
//        Challenge challenge1 = challengeStorage.createChallenge(challenge);
//        System.out.println("challenge1 = " + challenge1);
//        System.out.println(challengeStorage.getChallengesByChatId("2"));
//        Submission submission = new Submission("5", "1", 10);
//        challengeStorage.createSubmissionByChallengeId(submission, "2");
//        System.out.println(challengeStorage.getSubmissionByChallengeId("2"));
//        System.out.println(challengeStorage.getChallengeProgress("2"));
//        System.out.println(challengeStorage.getChallenge("2").getSubmissions());
    }

    private static final Integer DEFAULT_PROGRESS = 0;

    private final ChallengeRepository challengeRepository = new ChallengeRepository();
    private final UsersChallengesRepository usersChallengesRepository = new UsersChallengesRepository();
    private final SubmissionRepository submissionRepisotory = new SubmissionRepository();

    private final IdGenerator idGenerator;

    public ChallengeStorage(IdGenerator idGenerator) {
        this.idGenerator = idGenerator;
    }

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
        Challenge challenge;
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
        return submissionRepisotory.getSubmissionByChallengeId(challengeId);
    }

    public void createSubmissionByChallengeId(Submission submission, String challengeId) {
        submissionRepisotory.createSubmissionByChallengeId(submission, challengeId);
        challengeRepository.updateChallengeProgress(submission.value(), challengeId);
    }

    public void updateChallengeState(String challengeId, ChallengeState challengeState) {
        challengeRepository.updateChallengeState(challengeId, challengeState);
    }
}
