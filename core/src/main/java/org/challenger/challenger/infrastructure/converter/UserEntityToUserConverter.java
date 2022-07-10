package org.challenger.challenger.infrastructure.converter;

import org.challenger.challenger.domain.User;
import org.challenger.challenger.infrastructure.persist.entity.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UserEntityToUserConverter extends BaseConverter<UserEntity, User> {
	@Override
	public User convert(UserEntity entity) {
		return User.builder()
			.id(entity.getId())
			.name(entity.getName())
			.firstName(entity.getFirstName())
			.lastName(entity.getLastName())
			.build();
	}
}
