package org.challenger.challenger.infrastructure.controller;

import lombok.RequiredArgsConstructor;
import org.challenger.challenger.infrastructure.controller.dto.ChallengeCreateRequest;
import org.challenger.challenger.infrastructure.controller.dto.ChallengeDto;
import org.challenger.challenger.infrastructure.converter.ObjectConverter;
import org.challenger.challenger.infrastructure.service.ChallengeService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/challenges")
public class ChallengeController {

	private final ChallengeService challengeService;
	private final ObjectConverter objectConverter;

	@GetMapping(value = "{path}")
	public void getChallenge(@PathVariable("path") String challengeId) {
		Object challenge = challengeService.getChallenge(challengeId);
		ChallengeDto convert = objectConverter.convert(challenge, ChallengeDto.class);
		System.out.println(challengeId);
	}

	@PostMapping
	public void createChallenge(@RequestBody ChallengeCreateRequest request) {
		System.out.println(request.name());
	}
}

