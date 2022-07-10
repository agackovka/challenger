package org.challenger.challenger.infrastructure.persist.repository;

import org.challenger.challenger.infrastructure.persist.entity.UsersChallengesEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UsersChallengesEntityRepository extends CrudRepository<UsersChallengesEntity, String> {
	List<UsersChallengesEntity> findAllByChallengeId(String challengeId);
}
