package org.challenger.challenger.infrastructure.persist.impl;

import lombok.RequiredArgsConstructor;
import org.challenger.challenger.domain.Submission;
import org.challenger.challenger.infrastructure.persist.repository.SubmissionEntityRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class SubmissionRepository implements org.challenger.challenger.domain.SubmissionRepository {

	private final SubmissionEntityRepository submissionEntityRepository;

	@Override
	public List<Submission> getSubmissionByChallengeId(String challengeId) {
		return submissionEntityRepository.findByChallengeId(challengeId)
			.map(entity -> new Submission(entity.getId(), entity.getUserId(), entity.getValue()))
			.toList();
	}

	@Override
	public void createSubmissionByChallengeId(Submission submission, String challengeId) {

	}
}
