package org.challenger.challenger.infrastructure.persist.impl;

import lombok.RequiredArgsConstructor;
import org.challenger.challenger.domain.User;
import org.challenger.challenger.infrastructure.converter.ObjectConverter;
import org.challenger.challenger.infrastructure.persist.entity.UserEntity;
import org.challenger.challenger.infrastructure.persist.repository.UserEntityRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@RequiredArgsConstructor
@Component
public class UserRepository implements org.challenger.challenger.domain.UserRepository {
	private final UserEntityRepository userEntityRepository;
	private final ObjectConverter objectConverter;
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

	@Override
	public Optional<User> findUserById(String userId) {
		return userEntityRepository.findById(userId)
			.map(userEntity -> objectConverter.convert(userEntity, User.class));
	}
}
