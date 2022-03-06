package org.challenger.challenger.infrastructure.service;

import org.challenger.challenger.domain.Challenge2;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class ChallengeService {
	@PostConstruct
	public void createChallenge() {
		Challenge2 asdf = new Challenge2("asdf");
		System.out.println('x');
	}
}
