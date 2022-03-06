package org.challenger.challenger.domain;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

//хранилище челенжей
@Slf4j
public class ChallengeStorage {

    private static final Integer DEFAULT_PROGRESS = 0;

    //список челенджей
    private final List<Challenge> listChallenges = new ArrayList<>(3);
    private final IdGenerator idGenerator;

    //+конструктор который будет принимать айди генератор
    public ChallengeStorage(IdGenerator idGenerator) {
        this.idGenerator = idGenerator;
    }

    //создать
    public void createChallenge(String name, Integer goal, String userId, List<String> ids) {
        Challenge challenge = new Challenge(idGenerator.generateId(), DEFAULT_PROGRESS, name, goal, userId, ids);
        listChallenges.add(challenge);
        log.info("createChallenge.exit; challengeId={}", challenge.getId());
    }

    // удалить
    public void deleteChallenge(String challengeId) {
        listChallenges.removeIf(challenge -> Objects.equals(challengeId, challenge.getId()));
    }

    // получить
    public Challenge getChallenge(String challengeId) {
        return listChallenges.stream()
            .filter(challenge -> Objects.equals(challengeId, challenge.getId()))
            .findFirst().orElseThrow(() -> new IllegalArgumentException("Challenge not found by id"));
    }


}
