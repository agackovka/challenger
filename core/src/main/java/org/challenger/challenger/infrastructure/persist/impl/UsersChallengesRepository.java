package org.challenger.challenger.infrastructure.persist.impl;

import lombok.RequiredArgsConstructor;
import org.challenger.challenger.domain.UsersChallenges;
import org.challenger.challenger.infrastructure.persist.entity.UsersChallengesEntity;
import org.challenger.challenger.infrastructure.persist.repository.UsersChallengesEntityRepository;
import org.springframework.stereotype.Component;

import java.util.List;
@RequiredArgsConstructor
@Component
public class UsersChallengesRepository implements org.challenger.challenger.domain.UsersChallengesRepository {
	private final UsersChallengesEntityRepository usersChallengesEntityRepository;

	@Override
	public List<UsersChallenges> getUsersIds(String challengeId) {
		return usersChallengesEntityRepository.findAllByChallengeId(challengeId)
			.map(entity -> new UsersChallenges(entity.getUserId(), entity.getChallengeId(), entity.getCreatedAt(), entity.getId()))
			.toList();
	}

	@Override
	public void linkUsersWithChallenge(List<String> ids, String challengeid) {
		for (String s : ids) {
			UsersChallengesEntity usersChallengesEntity = UsersChallengesEntity.builder()
				.userId(s)
				.challengeId(challengeid)
				.build();
			usersChallengesEntityRepository.save(usersChallengesEntity);
		}
	}
}
