package org.challenger.challenger.infrastructure.converter;

import org.challenger.challenger.domain.Challenge;
import org.challenger.challenger.domain.ChallengeState;
import org.challenger.challenger.domain.ChallengeType;
import org.challenger.challenger.infrastructure.persist.entity.ChallengeEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class ChallengeToChallengeEntityConverter extends BaseConverter<Challenge, ChallengeEntity> {
	@Override
	public ChallengeEntity convert(Challenge domain) {
		return ChallengeEntity.builder()
			.id(domain.getId())
			.type(domain.getType().name())
			.state(domain.getState().name())
			.progress(domain.getProgress())
			.name(domain.getName())
			.goal(domain.getGoal())
			.ownerUserId(domain.getOwnerUserId())
			.chatId(domain.getChatId())
			.buttons(domain.getButtons())
			.build();
	}
}
