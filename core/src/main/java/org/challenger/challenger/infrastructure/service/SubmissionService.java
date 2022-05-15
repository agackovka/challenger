package org.challenger.challenger.infrastructure.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class SubmissionService {

	private final ChallengeService challengeService;

	public void submit(String userId, String challengeId, Integer submissionValue) {
		log.info("submit.enter; challengeId={} submissionValue={}", challengeId, submissionValue);
		challengeService.submit(userId, challengeId, submissionValue);
	}

}
