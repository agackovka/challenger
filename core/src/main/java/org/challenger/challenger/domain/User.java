package org.challenger.challenger.domain;

import lombok.Builder;

@Builder
public record User(String id, String name, String firstName, String lastName) {
}
