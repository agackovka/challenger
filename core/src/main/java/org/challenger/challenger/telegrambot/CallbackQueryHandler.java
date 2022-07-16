package org.challenger.challenger.telegrambot;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.challenger.challenger.domain.Challenge;
import org.challenger.challenger.domain.ChallengeState;
import org.challenger.challenger.infrastructure.service.ChallengeService;
import org.challenger.challenger.infrastructure.service.UserService;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.challenger.challenger.telegrambot.BotUtils.*;

@Slf4j
@RequiredArgsConstructor
public class CallbackQueryHandler {

	private final ChallengeService challengeService;
	private final UserService userService;

	public BotApiMethod<?> processCallbackQuery(CallbackQuery callbackQuery) {
		String chatId = callbackQuery.getMessage().getChatId().toString();
		User user = callbackQuery.getFrom();
		String userId = String.valueOf(user.getId());
		log.info("Command data: {}", callbackQuery.getData());
		String[] cbData = callbackQuery.getData().split(" ");

		// TODO will have to preserve userData in the database - firstname, lastName, userName
		// TODO will have to check whether the preserved data is correct enough
		String userName = user.getUserName() != null ? user.getUserName() : user.getFirstName() + " " + user.getLastName();

		String cbCommand = cbData[0];
		String cbFirstParam = cbData.length > 1 ? cbData[1] : null;

		Challenge challenge = null;
		if (cbFirstParam != null) {
			challenge = challengeService.getChallenge(cbFirstParam);
		}

		switch (cbCommand) {
			case MessageHandler.CHALLENGE_START:
				return getText(chatId, "In order to create a challenge use " +
					"/challenge create {challengeName} {goal} {button1} {button2} {button3}");

			case MessageHandler.CHALLENGE_DETAILS:
				return buildDetailsButtons(chatId, challenge);

			case MessageHandler.CHALLENGE_JOIN:
				if (challenge.getUserIds().contains(userId)) {
					return getText(chatId, userName + " already here");
				} else {
					challengeService.linkUsersWithChallenge(List.of(userId), challenge.getId());
				}
				return getText(chatId, userName + " joined");

			case MessageHandler.CHALLENGE_ACTIVATE:
				if (challenge.getUserIds().isEmpty()) {
					return getText(chatId, "Challenge cannot be activated without participants");
				} else {
					if (challenge.getState() == ChallengeState.INITIAL) {
						challengeService.updateChallengeState(challenge.getId(), ChallengeState.ACTIVATED);
						challenge.setState(ChallengeState.ACTIVATED);
						return buildDetailsButtons(chatId, challenge);
					} else {
						return getText(chatId, "Challenge already activated");
					}
				}

			case MessageHandler.CHALLENGE_SUBMIT:
				if (cbData.length < 3) {
					log.error("Cannot be submitted due to the error");
					return getText(chatId, "Cannot be submitted due to the error");
				} else {
					String value = cbData[2];
					// the value is validated here
					challengeService.submit(userId, challenge.getId(), Integer.valueOf(value));
					String handle = userService.calculateUserHandle(userId);
					return getText(chatId, "%s submitted %s".formatted(handle, value));
				}

		}
		log.info("processCallbackQuery.enter;");
		return null;
	}

	private SendMessage buildDetailsButtons(String chatId, Challenge challenge) {
		List<InlineKeyboardButton> submitButtons = getSubmitButtons(challenge);

		submitButtons.add(0, buildButton("I'm in", appendParam(MessageHandler.CHALLENGE_JOIN, challenge.getId())));
		submitButtons.add(1, buildButton("Activate challenge", appendParam(MessageHandler.CHALLENGE_ACTIVATE, challenge.getId())));

		return buildKeyboard(chatId, challenge.toString(), submitButtons);
	}

	private List<InlineKeyboardButton> getSubmitButtons(Challenge challenge) {
		String buttons = ObjectUtils.defaultIfNull(challenge.getButtons(), "");
		return Arrays.stream(buttons.split(","))
			.map(value -> buildButton(
				"Submit %s".formatted(value),
				appendParam(MessageHandler.CHALLENGE_SUBMIT, challenge.getId(), value))
			)
			.collect(Collectors.toList());
	}
}
