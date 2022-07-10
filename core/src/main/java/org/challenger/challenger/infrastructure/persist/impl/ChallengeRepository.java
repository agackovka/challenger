package org.challenger.challenger.infrastructure.persist.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.challenger.challenger.domain.Challenge;
import org.challenger.challenger.domain.ChallengeState;
import org.challenger.challenger.domain.ChallengeType;
import org.challenger.challenger.infrastructure.converter.ObjectConverter;
import org.challenger.challenger.infrastructure.persist.entity.ChallengeEntity;
import org.challenger.challenger.infrastructure.persist.repository.ChallengeEntityRepository;
import org.springframework.data.jdbc.core.JdbcAggregateTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
@Component
public class ChallengeRepository implements org.challenger.challenger.domain.ChallengeRepository {
	private final ChallengeEntityRepository challengeEntityRepository;
	private final ObjectConverter objectConverter;
	private final UsersChallengesRepository usersChallengesRepository;
	private final JdbcAggregateTemplate template;

	@Override
	public void create(Challenge challenge) {
		ChallengeEntity challengeEntity = objectConverter.convert(challenge, ChallengeEntity.class);
		template.insert(challengeEntity);
		usersChallengesRepository.linkUsersWithChallenge(challenge.getId(), challenge.getUserIds());
	}

	@Override
	public Optional<Challenge> getChallengeById(String challengeId) {
		return challengeEntityRepository.findById(challengeId)
			.map(entity -> objectConverter.convert(entity, Challenge.class));
	}

	@Override
	public List<Challenge> getChallengesByChatId(String chatId) {
		return challengeEntityRepository.findByChatId(chatId)
			.stream()
			.map(entity -> objectConverter.convert(entity, Challenge.class))
			.toList();
	}

	@Override
	public Integer getChallengeProgress(String challengeId) {
		return challengeEntityRepository.findById(challengeId)
			.map(ChallengeEntity::getProgress)
			.orElse(null);
	}

	@Override
	public void updateChallengeProgress(String challengeId, int value) {
		challengeEntityRepository.updateChallengeProgress(challengeId, value);
	}

	@Override
	public void updateChallengeState(String challengeId, ChallengeState challengeState) {
		Integer updatedCount = challengeEntityRepository.updateState(challengeId, challengeState);
		if (updatedCount == 1) {
			log.info("Successfully updated: id={}, state={}", challengeId, challengeState);
		} else if (updatedCount == 0) {
			throw new IllegalArgumentException("Challenge not found: id=%s".formatted(challengeId));
		} else {
			log.error("Updated more than one entity");
		}
	}
}
