package org.challenger.challenger.infrastructure.persist.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.Instant;
import java.util.Objects;

@Getter
@Setter
@Builder
@Table("submissions")
public final class SubmissionEntity {
	@Id
	private String id;
	private String userId;
	private Integer value;
	private String challengeId;
	private Instant createdAt;
}
