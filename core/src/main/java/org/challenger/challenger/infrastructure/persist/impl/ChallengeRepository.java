package org.challenger.challenger.infrastructure.persist.impl;

import lombok.RequiredArgsConstructor;
import org.challenger.challenger.domain.Challenge;
import org.challenger.challenger.domain.ChallengeState;
import org.challenger.challenger.domain.ChallengeType;
import org.challenger.challenger.domain.Submission;
import org.challenger.challenger.infrastructure.persist.entity.ChallengeEntity;
import org.challenger.challenger.infrastructure.persist.repository.ChallengeEntityRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Component
public class ChallengeRepository implements org.challenger.challenger.domain.ChallengeRepository {
	private final ChallengeEntityRepository challengeEntityRepository;

	@Override
	public void create(Challenge challenge) {
		ChallengeEntity challengeEntity = ChallengeEntity.builder()
			.id(challenge.getId())
			.type(challenge.getType().toString())
			.state(challenge.getState().toString())
			.progress(challenge.getProgress())
			.name(challenge.getName())
			.goal(challenge.getGoal())
			.ownerUserId(challenge.getOwnerUserId())
			.chatId(challenge.getChatId())
			.buttons(challenge.getButtons())
			.build();
		challengeEntityRepository.save(challengeEntity);
	}

	@Override
	public Challenge getChallengeById(String challengeId) {
		return challengeEntityRepository.findById(challengeId)
			.map(entity -> Challenge.builder()
				.id(entity.getId())
				.type(ChallengeType.valueOf(entity.getType()))
				.state(ChallengeState.valueOf(entity.getState()))
				.progress(entity.getProgress())
				.name(entity.getName())
				.goal(entity.getGoal())
				.ownerUserId(entity.getOwnerUserId())
				.userIds(new ArrayList<>())
				.chatId(entity.getChatId())
				.submissions(new ArrayList<>())
				.buttons(entity.getButtons())
				.build()
		).orElse(null);
	}

	@Override
	public List<Challenge> getChallengesByChatId(String chatId) {
		return challengeEntityRepository.findByChatId(chatId)
			.map(entity -> Challenge.builder()
				.id(entity.getId())
				.type(ChallengeType.valueOf(entity.getType()))
				.state(ChallengeState.valueOf(entity.getState()))
				.progress(entity.getProgress())
				.name(entity.getName())
				.goal(entity.getGoal())
				.ownerUserId(entity.getOwnerUserId())
				.userIds(new ArrayList<>())
				.chatId(entity.getChatId())
				.submissions(new ArrayList<>())
				.buttons(entity.getButtons())
				.build()
			).toList();
	}

	@Override
	public Integer getChallengeProgress(String challengeId) {
		return challengeEntityRepository.findById(challengeId)
			.map(ChallengeEntity::getProgress
			).orElse(null);
	}

	@Override
	public void updateChallengeProgress(int value, String challengeId) {
		ChallengeEntity challengeEntity = challengeEntityRepository.findById(challengeId).orElse(null);
		assert challengeEntity != null;
		challengeEntity.setProgress(value);
		challengeEntityRepository.save(challengeEntity);
	}

	@Override
	public void updateChallengeState(String challengeId, ChallengeState challengeState) {
		ChallengeEntity challengeEntity = challengeEntityRepository.findById(challengeId).orElse(null);
		assert challengeEntity != null;
		challengeEntity.setState(challengeState.toString());
		challengeEntityRepository.save(challengeEntity);
	}
}
