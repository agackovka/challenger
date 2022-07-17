package org.challenger.challenger.telegrambot;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.challenger.challenger.domain.Challenge;
import org.challenger.challenger.infrastructure.service.ChallengeService;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static org.challenger.challenger.telegrambot.BotUtils.*;

@Slf4j
@RequiredArgsConstructor
public class MessageHandler {

	public static final String CHALLENGE_START = "challenge_start";
	public static final String CHALLENGE_DETAILS = "challenge_details";

	public static final String CHALLENGE_GENERATE_UI_URL = "challenge_generate_ui_url";
	public static final String CHALLENGE_JOIN = "challenge_join";
	public static final String CHALLENGE_SUBMIT = "challenge_submit";
	public static final String CHALLENGE_ACTIVATE = "challenge_activate";

	public static final String CREATE_SUBCOMMAND = "create";

	private final ChallengeService challengeService;

	public static final String STATUS_COMMAND = "/status";
	public static final String CHALLENGE_COMMAND = "/challenge";


	public BotApiMethod<?> answerMessage(Message message) {
		String chatId = message.getChatId().toString();
		String userId = String.valueOf(message.getFrom().getId());
		String text = message.getText();

		if (text != null) {
			String[] messagePieces = text.split(" ");

			String command = messagePieces[0];
			int paramsNumber = messagePieces.length - 1; // single command + N-1 params

			log.info("answerMessage; text={}", text);

			List<Challenge> challenges = challengeService.findChallengeByChatId(chatId);
			switch (command) {
				case CHALLENGE_COMMAND:
					if (paramsNumber == 0) {
						if (challenges.isEmpty()) {
							return buildKeyboard(
								chatId, "Welcome, we have no active challenges so only these things are available for you: ",
								buildButton("Start new challenge", CHALLENGE_START)
							);
						} else {
							List<InlineKeyboardButton> challengeButtons = new ArrayList<>();

							challengeButtons.add(
								buildButton(
									"Generate UI link",
									CHALLENGE_GENERATE_UI_URL
								)
							);

							challengeButtons.addAll(challenges.stream()
								.map(c -> buildButton(
									"%s, %s from %s".formatted(c.getName(), c.getProgress(), c.getGoal()),
									appendParam(CHALLENGE_DETAILS, c.getId()))
								).toList());
							challengeButtons.add(buildButton("Start new challenge", CHALLENGE_START));
							return buildKeyboard(chatId, "List of challenges: ", challengeButtons);
						}
					} else {
						if (paramsNumber >= 3) {
							String subCommand = messagePieces[1];
							String name = messagePieces[2];

							int goal;
							try {
								goal = Integer.parseInt(messagePieces[3]);
							} catch (NumberFormatException e) {
								return getText(chatId, "Goal should be a number");
							}

							if (Objects.equals(subCommand, CREATE_SUBCOMMAND)) {
								List<String> buttons = new ArrayList<>();
								for (int i = 4; i < messagePieces.length; i++) {
									try {
										buttons.add(messagePieces[i]);
									} catch (NumberFormatException e) {
										return getText(chatId, "Only numbers are allowed for buttons");
									}
								}

								Challenge challenge =
									challengeService.createChallenge(name, goal, userId, new ArrayList<>(), chatId, buttons);

								return buildKeyboard(
									chatId, challenge.toString(),
									buildButton(
										"I'm in",
										appendParam(MessageHandler.CHALLENGE_JOIN, challenge.getId())
									),
									buildButton(
										"Activate challenge",
										appendParam(MessageHandler.CHALLENGE_ACTIVATE, challenge.getId())
									)
								);
							}
						}
					}

				case STATUS_COMMAND:
			}
		}

		log.info("proceed as is");

		return null;
	}

}
