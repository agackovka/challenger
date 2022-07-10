package org.challenger.challenger.infrastructure.persist.repository;

import org.challenger.challenger.infrastructure.persist.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;

public interface UserEntityRepository extends CrudRepository<UserEntity, String> {
}
