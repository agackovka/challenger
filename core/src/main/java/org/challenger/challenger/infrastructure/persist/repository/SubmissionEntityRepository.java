package org.challenger.challenger.infrastructure.persist.repository;

import org.challenger.challenger.infrastructure.persist.entity.SubmissionEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.stream.Stream;

public interface SubmissionEntityRepository extends CrudRepository<SubmissionEntity, String> {

	Stream<SubmissionEntity> findByChallengeId(String challengeId);

}
