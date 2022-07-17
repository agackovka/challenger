package org.challenger.challenger.infrastructure.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.challenger.challenger.telegrambot.WriteReadBot;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Objects;

@Slf4j
@RestController
@AllArgsConstructor
public class WebhookController {
	private final WriteReadBot writeReadBot;

	@PostMapping("/")
	public BotApiMethod<?> onUpdateReceived(@RequestBody Update update) {
		return writeReadBot.onWebhookUpdateReceived(update);
	}
}