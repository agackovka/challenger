package org.challenger.challenger.telegrambot;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.challenger.challenger.domain.Challenge;

import java.util.*;

@Slf4j
public class ChallengeSessionStorage {

	private final Map<String, ChallengeSession> challengeIdIndex = Collections.synchronizedMap(new HashMap<>());
	private final Map<String, List<ChallengeSession>> chatChallenges = Collections.synchronizedMap(new HashMap<>());

	public void put(ChallengeSession challengeSession) {
		Challenge challenge = challengeSession.getChallenge();
		String challengeId = challenge.getId();
		challengeIdIndex.put(challengeId, challengeSession);
		List<ChallengeSession> innerList = Optional.ofNullable(chatChallenges.get(challenge.getChatId()))
			.orElse(new ArrayList<>());

		innerList.removeIf(c -> Objects.equals(challengeId, c.getChallenge().getId()));

		innerList.add(challengeSession);
		chatChallenges.put(challenge.getChatId(), innerList);
	}

	public ChallengeSession get(String challengeId) {
		return challengeIdIndex.get(challengeId);
	}

	public Optional<ChallengeSession> findActiveSessionInChat(String chatId, String ownerUserId) {
		log.info("findActiveSessionInChat.enter; chatId={} ownerUserId={}", chatId, ownerUserId);
		List<ChallengeSession> chatSessions = ObjectUtils.defaultIfNull(chatChallenges.get(chatId), new ArrayList<>());
		return chatSessions.stream()
			.filter(c -> EditState.FINISHED != c.getEditState())
			.filter(c -> Objects.equals(c.getChallenge().getOwnerUserId(), ownerUserId))
			.findAny();
	}
}
