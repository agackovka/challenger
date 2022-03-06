package org.challenger.challenger.infrastructure.controller.dto;

import java.util.List;

public record ChallengeCreateRequest(String name, Integer goal, String ownerUserId, List<String> userIds) {
}
