package org.challenger.challenger.domain;

import java.util.List;
import java.util.Optional;

public interface ChallengeRepository {
	void create(Challenge challenge);

	Optional<Challenge> getChallengeById(String challengeId);

	List<Challenge> getChallengesByChatId(String chatId);

	Integer getChallengeProgress(String challengeId);

	void updateChallengeProgress(String challengeId, int value);

	void updateChallengeState(String challengeId, ChallengeState challengeState);
}
