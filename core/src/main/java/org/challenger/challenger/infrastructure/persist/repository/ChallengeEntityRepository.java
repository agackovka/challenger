package org.challenger.challenger.infrastructure.persist.repository;

import org.challenger.challenger.infrastructure.persist.entity.ChallengeEntity;
import org.challenger.challenger.infrastructure.persist.entity.SubmissionEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.stream.Stream;

public interface ChallengeEntityRepository extends CrudRepository<ChallengeEntity, String> {
	Stream<ChallengeEntity> findByChatId(String chatId);
}
