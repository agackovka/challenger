package org.challenger.challenger.infrastructure.controller.dto;

public record SubmitRequest(String userId, String challengeId, Integer value) {
}
