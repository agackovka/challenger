package org.challenger.challenger.service;

import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class ChallengeService {

	@PostConstruct
	public void notifyAboutStart() {
		System.out.println("Started!!!");
	}

}
