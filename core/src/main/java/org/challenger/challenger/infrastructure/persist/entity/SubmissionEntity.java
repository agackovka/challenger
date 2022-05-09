package org.challenger.challenger.infrastructure.persist.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.Objects;

@Getter
@Setter
@Table("submissions")
public final class SubmissionEntity {
	@Id
	private String id;
	private String userId;
	private Integer value;
	private String challengeId;
}
