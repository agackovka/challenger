package org.challenger.challenger.infrastructure.persist.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Builder
@Getter
@Setter
@Table("users")
public class UserEntity {
	@Id
	private String id;
	private String name;
	private String firstName;
	private String lastName;
}
