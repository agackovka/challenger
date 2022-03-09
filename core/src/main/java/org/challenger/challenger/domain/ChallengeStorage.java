package org.challenger.challenger.domain;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

// хранилище челенжей
@Slf4j
public class ChallengeStorage {

    private static final Integer DEFAULT_PROGRESS = 0;

    // список челенджей
    private final List<Challenge> challenges = new ArrayList<>(3);
    private final IdGenerator idGenerator;

    //+конструктор который будет принимать айди генератор
    public ChallengeStorage(IdGenerator idGenerator) {
        this.idGenerator = idGenerator;
    }

    // создать
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

        challenges.add(challenge);
        log.info("createChallenge.exit; challengeId={}", challenge.getId());

        return challenge;
    }

    // удалить
    public void deleteChallenge(String challengeId) {
        challenges.removeIf(challenge -> Objects.equals(challengeId, challenge.getId()));
    }

    // получить
    public Challenge getChallenge(String challengeId) {
        return challenges.stream()
                .filter(challenge -> Objects.equals(challengeId, challenge.getId()))
                .findFirst().orElse(null);
    }

    public List<Challenge> getChallengeByChatId(String chatId) {
        return challenges.stream()
            .filter(challenge -> Objects.equals(chatId, challenge.getChatId()))
            .toList();
    }

}
