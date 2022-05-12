package org.challenger.challenger.infrastructure.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.challenger.challenger.domain.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.util.UUID;

@Configuration
public class BaseConfiguration {

	@Bean
	public ChallengeStorage challengeStorage(ChallengeRepository challengeRepository, UsersChallengesRepository usersChallengesRepository, SubmissionRepository submissionRepository, IdGenerator idGenerator) {
		return new ChallengeStorage(challengeRepository, usersChallengesRepository, submissionRepository,idGenerator);
	}

	@Bean
	public IdGenerator idGenerator() {
		return () -> UUID.randomUUID().toString();
	}

	@Bean
	public ObjectMapper objectMapper() {
		return new ObjectMapper();
	}

	@Bean
	public TelegramBotsApi telegramBotsApi() throws TelegramApiException {
		return new TelegramBotsApi(DefaultBotSession.class);
	}
}
