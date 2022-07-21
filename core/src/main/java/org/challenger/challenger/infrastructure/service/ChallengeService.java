package org.challenger.challenger.infrastructure.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.challenger.challenger.domain.*;
import org.challenger.challenger.infrastructure.exception.IncorrectActivationException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChallengeService {

    private final ChallengeStorage challengeStorage;

    private final UserService userService;
    private final IdGenerator idGenerator;

    public String printChallenge(Challenge challenge) {
        return """
            id: %s
            name: %s
            users: %s
            goal: %s
            progress: %s
            state: %s
            attempts: %s
            """.formatted(
                    challenge.getId(),
                    challenge.getName(),
                    handleChallengesUsers(challenge),
                    challenge.getGoal(),
                    challenge.getProgress(),
                    challenge.getState(),
                    gatherSubmissionsByUser(challenge)
        );
    }

    public List<String> handleChallengesUsers(Challenge challenge) {
        return challenge.getUserIds().stream()
            .map(userService::calculateUserHandle)
            .toList();
    }

    private Map<String, Long> gatherSubmissionsByUser(Challenge challenge) {
        return challenge.getSubmissions().stream()
            .map(submission -> new Submission(
                submission.id(),
                userService.calculateUserHandle(submission.userId()),
                submission.value()))
            .collect(Collectors.groupingBy(Submission::userId, Collectors.counting()));
    }
    public Challenge createChallenge(String name, Integer goal, String userId, List<String> ids, String chatId, List<String> buttons) {
        log.info("createChallenge; challenge.name = {}", name);
        return challengeStorage.createChallenge(name, goal, userId, ids, chatId, StringUtils.join(buttons, ", "));
    }

    public Challenge getChallenge(String challengeId) {
        log.info("getChallenge.enter; challengeId={}", challengeId);
        return challengeStorage.getChallenge(challengeId);
    }

    public List<Challenge> getChallengesByChatId(String chatId) {
        log.info("getChallengesByChatId.enter; challengeId={}", chatId);
        return challengeStorage.getChallengesByChatId(chatId);
    }

    public void submit(String userId, String challengeId, Integer submissionValue) {
        log.info("submit.enter; userId={} challengeId={} submissionValue={}", userId, challengeId, submissionValue);
        challengeStorage.submit(challengeId, new Submission(idGenerator.generateId(), userId, submissionValue));
        log.info("submit.exit; challenge.progress={}", challengeStorage.getChallengeProgress(challengeId));
    }

    public List<Challenge> findChallengeByChatId(String chatId) {
        log.info("findChallengeByChatId.enter; chatId={}", chatId);
        return challengeStorage.getChallengesByChatId(chatId);
    }

    public void linkUsersWithChallenge(List<String> ids, String challengeId) {
        log.info("linkUsersWithChallenge; challenge.id = {}", challengeId);
        challengeStorage.linkUsersWithChallenge(ids, challengeId);
    }

    public void updateChallengeState(String challengeId, ChallengeState challengeState) {
        log.info("updateChallengeState; challenge.id = {}, challengeState = {}", challengeId, challengeState);
        challengeStorage.updateChallengeState(challengeId, challengeState);
    }

    public void activate(String challengeId) {
        Challenge challenge = challengeStorage.getChallenge(challengeId);
        if (challenge.getState() == ChallengeState.INITIAL) {
            challengeStorage.activateChallenge(challengeId);
        } else if (challenge.getState() == ChallengeState.ACTIVATED) {
            throw new IncorrectActivationException("Already activated");
        } else {
            throw new IncorrectActivationException("Incorrect state");
        }
        log.info("activate.exit; challenge.state={}", challenge.getState());
    }

}
