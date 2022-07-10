package org.challenger.challenger.infrastructure.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.challenger.challenger.infrastructure.persist.entity.UserEntity;
import org.challenger.challenger.infrastructure.persist.repository.UserEntityRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
public class UserService {

	private final UserEntityRepository userEntityRepository;

	public String calculateUserHandle(String userId) {
		return userEntityRepository.findById(userId).map(userEntity -> {
			String name = userEntity.getName();
			String firstName = userEntity.getFirstName();
			String lastName = userEntity.getLastName();
			String calculatedFirstLastName = calculateFirstLastName(firstName, lastName);
			return StringUtils.firstNonBlank(calculatedFirstLastName, name);
		}).orElse(userId);
		// null will be returned for null input, that isn't a good idea
	}

	private String calculateFirstLastName(String firstName, String lastName) {
		if (StringUtils.isNotBlank(firstName) && StringUtils.isNotBlank(lastName)) {
			return firstName + " " + lastName;
		} else if (StringUtils.isNotBlank(firstName)) {
			return firstName;
		} else if (StringUtils.isNotBlank(lastName)) {
			return lastName;
		} else {
			return null;
		}
	}

}
