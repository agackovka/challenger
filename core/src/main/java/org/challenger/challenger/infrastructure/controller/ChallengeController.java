package org.challenger.challenger.infrastructure.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.challenger.challenger.domain.Challenge;
import org.challenger.challenger.infrastructure.controller.dto.ChallengeCreateRequest;
import org.challenger.challenger.infrastructure.controller.dto.ChallengeCreateResponse;
import org.challenger.challenger.infrastructure.controller.dto.ChallengeDto;
import org.challenger.challenger.infrastructure.converter.ObjectConverter;
import org.challenger.challenger.infrastructure.service.ChallengeService;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/challenges")
public class ChallengeController {

	private final ChallengeService challengeService;
	private final ObjectConverter objectConverter;

	@PostMapping
	public ChallengeCreateResponse createChallenge(@RequestBody ChallengeCreateRequest request) {
		log.info("createChallenge.enter; name={}", request.name());
		Challenge challenge = challengeService.createChallenge(
			request.name(),
			request.goal(),
			request.ownerUserId(),
			request.userIds(),
			request.chatId()
		);
		return new ChallengeCreateResponse(challenge.getId());
	}

	@GetMapping(value = "{path}")
	public ChallengeDto getChallenge(@PathVariable("path") String challengeId) {
		Challenge challenge = challengeService.getChallenge(challengeId);
		return objectConverter.convert(challenge, ChallengeDto.class);
	}

}

