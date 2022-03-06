package org.challenger.challenger.domain;

import org.challenger.challenger.domain.Challenge;

import java.util.ArrayList;
import java.util.List;

//хранилище челенжей
public class ChallengeStorage {

    //список челенджей
    private final List<Challenge> listChallenges = new ArrayList<>(3);
    private final List<User> listUsers = new ArrayList<>(3);

    private final IdGenerator idGenerator;

    //+конструктор который будет принимать айди генератор
    public ChallengeStorage(IdGenerator idGenerator) {
        this.idGenerator = idGenerator;
    }

    //создать
    public void createChallenge(String name, String type, Integer goal, Integer progress, Integer userId, String userEmail, Integer submissionId) {
        Challenge challenge = new Challenge(idGenerator.generateId(), name, type, goal, progress, userId, userEmail, submissionId);
        listChallenges.add(challenge);
        User user = new User(idGenerator.generateId(), userEmail);
        listUsers.add(user);
    }

    // удалить
    public void deleteChallenge(int x) {
        listChallenges.remove(x);
    }

    // получить
    public Challenge receiveChallenge(int x) {
        return listChallenges.get(x);
    }

    // сабмишины юзеры (+email) задейстовать
    // сабмишин

}
