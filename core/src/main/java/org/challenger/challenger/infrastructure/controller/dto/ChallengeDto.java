package org.challenger.challenger.infrastructure.controller.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record ChallengeDto(
	String id,
	String name,
	String type,
	String state,
	Integer goal,
	Integer progress,
	String owner,
	List<String> users,
	List<SubmissionDto> submissions
) {
	public record SubmissionDto(String user, Integer value) {}
}

