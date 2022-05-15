package org.challenger.challenger.infrastructure.persist.impl;

import lombok.RequiredArgsConstructor;
import org.challenger.challenger.domain.Submission;
import org.challenger.challenger.infrastructure.persist.entity.SubmissionEntity;
import org.challenger.challenger.infrastructure.persist.repository.SubmissionEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jdbc.core.JdbcAggregateTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class SubmissionRepository implements org.challenger.challenger.domain.SubmissionRepository {

	private final SubmissionEntityRepository submissionEntityRepository;
	private final JdbcAggregateTemplate template;

	@Override
	public List<Submission> getSubmissionByChallengeId(String challengeId) {
		return submissionEntityRepository.findByChallengeId(challengeId)
			.map(entity -> new Submission(entity.getId(), entity.getUserId(), entity.getValue()))
			.toList();
	}

	@Override
	public void createSubmissionByChallengeId(String challengeId, Submission submission) {
		SubmissionEntity entity = SubmissionEntity.builder()
			.challengeId(challengeId)
			.id(submission.id())
			.userId(submission.userId())
			.value(submission.value())
			.build();
		// TODO not the most obvious thing to distinguish between insert and update here since we are creating id
		// by our idGenerator
		template.insert(entity);
	}
}
