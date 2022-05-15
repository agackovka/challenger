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

	@PostMapping("/{token}")
	public BotApiMethod<?> onUpdateReceived(@PathVariable("token") String token, @RequestBody Update update) {
		String secretToken = "4321";
		System.out.println("secretToken = " + secretToken);
		if (!Objects.equals(token, secretToken)) {
			log.info("Choose correct secrettoken");
			throw new IllegalArgumentException("unauthorized");
		}
		return writeReadBot.onWebhookUpdateReceived(update);
	}
}