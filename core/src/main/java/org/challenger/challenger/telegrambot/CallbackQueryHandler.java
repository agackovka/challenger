package org.challenger.challenger.telegrambot;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.challenger.challenger.domain.Challenge;
import org.challenger.challenger.domain.ChallengeState;
import org.challenger.challenger.infrastructure.service.ChallengeService;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.List;
import java.util.stream.Collectors;

import static org.challenger.challenger.telegrambot.BotUtils.*;

@Slf4j
@RequiredArgsConstructor
public class CallbackQueryHandler {

	private final ChallengeService challengeService;
	private final ChallengeSessionStorage challengeSessionStorage;

	public BotApiMethod<?> processCallbackQuery(CallbackQuery callbackQuery) {
		String chatId = callbackQuery.getMessage().getChatId().toString();
		String userId = callbackQuery.getFrom().getUserName();
		String[] cbData = callbackQuery.getData().split(" ");

		String cbCommand = cbData[0];
		String cbFirstParam = cbData.length > 1 ? cbData[1] : null;

		Challenge challenge = challengeService.getChallenge(cbFirstParam);
		ChallengeSession sess = challengeSessionStorage.get(challenge.getId());

		switch (cbCommand) {
			case MessageHandler.CHALLENGE_START:
				return getText(chatId, "In order to create a challenge use " +
					"/challenge create {challengeName} {goal} {button1} {button2} {button3}");

			case MessageHandler.CHALLENGE_DETAILS:
				return buildDetailsButtons(chatId, sess);

			case MessageHandler.CHALLENGE_JOIN:
				if (challenge.getUserIds().contains(userId)) {
					return getText(chatId, userId + " already here");
				} else {
					challenge.getUserIds().add(userId);
				}
				return getText(chatId, userId + " joined");

			case MessageHandler.CHALLENGE_ACTIVATE:
				if (challenge.getUserIds().isEmpty()) {
					return getText(chatId, "Challenge cannot be activated without participants");
				} else {
					if (challenge.getState() == ChallengeState.INITIAL) {
						challenge.setState(ChallengeState.ACTIVATED);
						ChallengeSession session = challengeSessionStorage.get(challenge.getId());
						return buildDetailsButtons(chatId, session);
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
					return getText(chatId, "%s submitted %s".formatted(userId, value));
				}

		}
		log.info("processCallbackQuery.enter;");
		return null;
	}

	private SendMessage buildDetailsButtons(String chatId, ChallengeSession sess) {
		List<InlineKeyboardButton> submitButtons = getSubmitButtons(sess);

		Challenge challenge = sess.getChallenge();

		submitButtons.add(0, buildButton("I'm in", appendParam(MessageHandler.CHALLENGE_JOIN, challenge.getId())));
		submitButtons.add(1, buildButton("Activate challenge", appendParam(MessageHandler.CHALLENGE_ACTIVATE, challenge.getId())));

		return buildKeyboard(chatId, challenge.toString(), submitButtons);
	}

	private List<InlineKeyboardButton> getSubmitButtons(ChallengeSession session) {
		Challenge challenge = session.getChallenge();
		return session.getButtons().stream()
			.map(value -> buildButton(
				"Submit %s".formatted(value),
				appendParam(MessageHandler.CHALLENGE_SUBMIT, challenge.getId(), value))
			)
			.collect(Collectors.toList());
	}
}
