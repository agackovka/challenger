package org.challenger.challenger.infrastructure.controller.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record ChallengeDto(
	String id, String name, String type, Integer goal, Integer progress, UserDto owner, List<UserDto> users
) {
	public record UserDto(String userId) {}
}

