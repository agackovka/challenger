package org.challenger.challenger.infrastructure.config;

import org.challenger.challenger.domain.ChallengeStorage;
import org.challenger.challenger.domain.IdGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.UUID;

@Configuration
public class BaseConfiguration {

	@Bean
	public ChallengeStorage challengeStorage(IdGenerator idGenerator) {
		return new ChallengeStorage(idGenerator);
	}

	@Bean
	public IdGenerator idGenerator() {
		return () -> UUID.randomUUID().toString();
	}

}
