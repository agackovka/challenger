package org.challenger.challenger.infrastructure.persist.repository;

import org.challenger.challenger.infrastructure.persist.entity.SubmissionEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SubmissionEntityRepository extends CrudRepository<SubmissionEntity, String> {

	List<SubmissionEntity> findByChallengeId(String challengeId);

}
