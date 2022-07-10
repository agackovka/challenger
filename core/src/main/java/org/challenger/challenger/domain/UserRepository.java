package org.challenger.challenger.domain;

import java.util.Optional;

public interface UserRepository {
	void addUser (String id, String name, String firstName, String lastName);
	Optional<User> findUserById(String userId);
}
