package org.challenger.challenger.infrastructure.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.challenger.challenger.domain.Challenge;
import org.challenger.challenger.domain.ChallengeStorage;
import org.challenger.challenger.domain.IdGenerator;
import org.challenger.challenger.domain.Submission;
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
        return challengeStorage.createChallenge(name, goal, userId, ids, chatId);
    }

    public Challenge getChallenge(String challengeId) {
        log.info("getChallenge.enter; challengeId={}", challengeId);
        return challengeStorage.getChallenge(challengeId);
    }

    public void submit(String userId, String challengeId, Integer submissionValue) {
        log.info("submit.enter; userId={} challengeId={} submissionValue={}", userId, challengeId, submissionValue);
        Challenge challenge = challengeStorage.getChallenge(challengeId);
        challenge.getSubmissions().add(new Submission(idGenerator.generateId(), userId, submissionValue));
        challenge.appendProgress(submissionValue);
        log.info("submit.exit; challenge.progress={}", challenge.getProgress());
    }

    public List<Challenge> findChallengeByChatId(String chatId) {
        log.info("findChallengeByChatId.enter; chatId={}", chatId);
        return challengeStorage.getChallengeByChatId(chatId);
    }
}
