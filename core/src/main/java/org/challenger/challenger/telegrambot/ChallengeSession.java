package org.challenger.challenger.telegrambot;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.challenger.challenger.domain.Challenge;

import java.util.List;

@Setter
@Getter
@Builder
@AllArgsConstructor
public class ChallengeSession {

	private EditState editState;

	private Challenge challenge;

	private List<Integer> buttons;
}
