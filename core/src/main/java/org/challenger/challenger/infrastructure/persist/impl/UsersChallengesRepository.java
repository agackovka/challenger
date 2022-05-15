package org.challenger.challenger.infrastructure.persist.impl;

import lombok.RequiredArgsConstructor;
import org.challenger.challenger.domain.IdGenerator;
import org.challenger.challenger.domain.UsersChallenges;
import org.challenger.challenger.infrastructure.persist.entity.UsersChallengesEntity;
import org.challenger.challenger.infrastructure.persist.repository.UsersChallengesEntityRepository;
import org.springframework.data.jdbc.core.JdbcAggregateTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
@RequiredArgsConstructor
@Component
public class UsersChallengesRepository implements org.challenger.challenger.domain.UsersChallengesRepository {
	private final UsersChallengesEntityRepository usersChallengesEntityRepository;
	private final JdbcAggregateTemplate template;
	private final IdGenerator idGenerator;

	@Override
	public List<UsersChallenges> getUsersIds(String challengeId) {
		return usersChallengesEntityRepository.findAllByChallengeId(challengeId)
			.map(entity -> new UsersChallenges(entity.getUserId(), entity.getChallengeId(), entity.getId()))
			.toList();
	}

	@Override
	public void linkUsersWithChallenge(String challengeId, List<String> ids) {
		for (String s : ids) {
			UsersChallengesEntity usersChallengesEntity = UsersChallengesEntity.builder()
				.id(idGenerator.generateId())
				.userId(s)
				.challengeId(challengeId)
				.build();
			template.insert(usersChallengesEntity);
		}
	}
}
