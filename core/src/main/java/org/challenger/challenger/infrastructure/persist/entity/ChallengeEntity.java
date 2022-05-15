package org.challenger.challenger.infrastructure.persist.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Builder
@Getter
@Setter
@Table("challenges")
public final class ChallengeEntity {
	@Id
	private String id;
	private Integer progress;
	private Integer goal;
	private String ownerUserId;
	private String chatId;
	private String name;
	private String type;
	private String state;
	private String buttons;
}
