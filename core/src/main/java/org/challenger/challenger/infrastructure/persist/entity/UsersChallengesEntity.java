package org.challenger.challenger.infrastructure.persist.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Builder
@Getter
@Setter
@Table("user_challenges")
public class UsersChallengesEntity {
	@Id
	private String userId;
	private String challengeId;
	private String createdAt;
	private Integer id;
}
