package org.challenger.challenger.infrastructure.persist.repository;

import org.challenger.challenger.domain.ChallengeState;
import org.challenger.challenger.infrastructure.persist.entity.ChallengeEntity;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.stream.Stream;

public interface ChallengeEntityRepository extends CrudRepository<ChallengeEntity, String> {
	Stream<ChallengeEntity> findByChatId(String chatId);

	@Modifying
	@Query("update challenges set state=:challengeState where id=:challengeId")
	Integer updateState(String challengeId, ChallengeState challengeState);

	@Modifying
	@Query("update challenges set progress=:value where id=:challengeId")
	Integer updateChallengeProgress(String challengeId, int value);
}
