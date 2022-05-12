package org.challenger.challenger.infrastructure.persist.impl;

import lombok.RequiredArgsConstructor;
import org.challenger.challenger.infrastructure.persist.entity.ChallengeEntity;
import org.challenger.challenger.infrastructure.persist.entity.UserEntity;
import org.challenger.challenger.infrastructure.persist.repository.UserEntityRepository;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class UserRepository implements org.challenger.challenger.domain.UserRepository {
	private final UserEntityRepository userEntityRepository;
	@Override
	public void addUser(String id, String name, String firstName, String lastName) {
		UserEntity userEntity = UserEntity.builder()
			.id(id)
			.name(name)
			.firstName(firstName)
			.lastName(lastName)
			.build();
		userEntityRepository.save(userEntity);
	}
}
