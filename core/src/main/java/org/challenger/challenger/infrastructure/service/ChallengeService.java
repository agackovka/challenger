package org.challenger.challenger.infrastructure.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.challenger.challenger.domain.Challenge;
import org.challenger.challenger.domain.ChallengeStorage;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChallengeService {

	private final ChallengeStorage challengeStorage;

	public void createChallenge(String name, Integer goal, String userId, List<String> ids) {
		// TODO no validation, will be added later
		challengeStorage.createChallenge(name, goal, userId, ids);
	}

	public Challenge getChallenge(String challengeId) {
		log.info("getChallenge.enter; challengeId={}", challengeId);
		return challengeStorage.getChallenge(challengeId);
	}
}
