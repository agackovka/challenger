package org.challenger.challenger.telegrambot;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updates.SetWebhook;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.starter.SpringWebhookBot;

import java.io.IOException;

@Slf4j
@Getter
@Setter
public class WriteReadBot extends SpringWebhookBot {
	private String botPath;
	private String botUsername;
	private String botToken;

	private MessageHandler messageHandler;
	private CallbackQueryHandler callbackQueryHandler;

	public WriteReadBot(SetWebhook setWebhook, MessageHandler messageHandler, CallbackQueryHandler callbackQueryHandler) {
		super(setWebhook);
		this.messageHandler = messageHandler;
		this.callbackQueryHandler = callbackQueryHandler;
	}

	@Override
	public BotApiMethod<?> onWebhookUpdateReceived(Update update) {
		log.info("onWebhookUpdateReceived");
		try {
			return handleUpdate(update);
		} catch (Exception e) {
			log.error("error happened", e);
		}
		// TODO error handling
		return null;
	}

	private BotApiMethod<?> handleUpdate(Update update) throws IOException {
		if (update.hasCallbackQuery()) {
			CallbackQuery callbackQuery = update.getCallbackQuery();
			return callbackQueryHandler.processCallbackQuery(callbackQuery);
		} else {
			Message message = update.getMessage();
			if (message != null) {
				return messageHandler.answerMessage(update.getMessage());
			}
		}
		return null;
	}
}