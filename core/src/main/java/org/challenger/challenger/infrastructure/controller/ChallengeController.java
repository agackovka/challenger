package org.challenger.challenger.infrastructure.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.challenger.challenger.domain.Challenge;
import org.challenger.challenger.infrastructure.controller.dto.*;
import org.challenger.challenger.infrastructure.converter.ObjectConverter;
import org.challenger.challenger.infrastructure.exception.IncorrectActivationException;
import org.challenger.challenger.infrastructure.service.ChallengeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

	@PostMapping("/activate")
	public void activate(@RequestBody ChallengeActivateDto challengeActivateDto) {
		log.info("submit.enter; challengeActivateDto.challengeId={} ", challengeActivateDto.challengeId());
		challengeService.activate(challengeActivateDto.challengeId());
	}

	@ExceptionHandler(IncorrectActivationException.class)
	public ResponseEntity<?> handleException() {
		return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
	}

}

