package org.challenger.challenger.infrastructure.persist.repository;

import org.challenger.challenger.domain.UsersChallengesRepository;
import org.challenger.challenger.infrastructure.persist.entity.UserEntity;
import org.challenger.challenger.infrastructure.persist.entity.UsersChallengesEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.stream.Stream;

public interface UsersChallengesEntityRepository extends CrudRepository<UsersChallengesEntity, String> {
	Stream<UsersChallengesEntity> findAllByChallengeId(String challengeId);
}
