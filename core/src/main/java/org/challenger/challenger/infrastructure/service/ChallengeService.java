package org.challenger.challenger.infrastructure.service;

import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class ChallengeService {
	@PostConstruct
	public void createChallenge() {
		System.out.println('x');
	}

	public Object getChallenge(String challengeId) {
		return null;
	}
}
