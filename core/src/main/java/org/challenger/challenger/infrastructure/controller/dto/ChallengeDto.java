package org.challenger.challenger.infrastructure.controller.dto;

import java.util.List;

public record ChallengeDto(
	String id, String name, String type, Integer goal, Integer progress, UserDto owner, List<UserDto> users
) {
	record UserDto(String email) {}
}

