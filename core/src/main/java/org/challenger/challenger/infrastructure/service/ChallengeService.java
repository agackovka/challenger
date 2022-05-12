package org.challenger.challenger.infrastructure.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.challenger.challenger.domain.*;
import org.challenger.challenger.infrastructure.exception.IncorrectActivationException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChallengeService {

    private final ChallengeStorage challengeStorage;
    private final IdGenerator idGenerator;

    public Challenge createEmptyChallenge(String userId, String chatId) {
        return challengeStorage.createChallenge(null, null, userId, new ArrayList<>(), chatId);
    }

    public Challenge createChallenge(String name, Integer goal, String userId, List<String> ids, String chatId) {
        log.info("createChallenge; challenge.name = {}", name);
        return challengeStorage.createChallenge(name, goal, userId, ids, chatId);
    }

    public Challenge createChallenge(Challenge challenge) {
        log.info("createChallenge; challenge.id = {}", challenge.getId());
        return challengeStorage.createChallenge(challenge);
    }

    public Challenge getChallenge(String challengeId) {
        log.info("getChallenge.enter; challengeId={}", challengeId);
        return challengeStorage.getChallenge(challengeId);
    }

    public void submit(String userId, String challengeId, Integer submissionValue) {
        log.info("submit.enter; userId={} challengeId={} submissionValue={}", userId, challengeId, submissionValue);
//        Challenge challenge = challengeStorage.getChallenge(challengeId);
//        challenge.getSubmissions().add(new Submission(idGenerator.generateId(), userId, submissionValue));
//        challenge.appendProgress(submissionValue);
        challengeStorage.createSubmissionByChallengeId(new Submission(idGenerator.generateId(), userId, submissionValue), challengeId);
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
            challenge.setState(ChallengeState.ACTIVATED);
        } else if (challenge.getState() == ChallengeState.ACTIVATED) {
            throw new IncorrectActivationException();
        } else {
            throw new IncorrectActivationException();
        }
        log.info("activate.exit; challenge.state={}", challenge.getState());
    }

}
