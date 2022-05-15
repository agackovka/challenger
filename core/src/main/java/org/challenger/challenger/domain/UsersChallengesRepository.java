package org.challenger.challenger.domain;

import java.util.List;

public interface UsersChallengesRepository {
	List<UsersChallenges> getUsersIds(String challengeId);

	void linkUsersWithChallenge(String challengeId, List<String> ids);
}
