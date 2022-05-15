package org.challenger.challenger.infrastructure.converter;

import org.challenger.challenger.domain.Challenge;
import org.challenger.challenger.domain.ChallengeState;
import org.challenger.challenger.domain.ChallengeType;
import org.challenger.challenger.infrastructure.persist.entity.ChallengeEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class ChallengeEntityToChallengeConverter extends BaseConverter<ChallengeEntity, Challenge> {
	@Override
	public Challenge convert(ChallengeEntity entity) {
		return Challenge.builder()
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
			.build();
	}
}
