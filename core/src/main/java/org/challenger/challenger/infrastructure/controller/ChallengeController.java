package org.challenger.challenger.infrastructure.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/challenges")
public class ChallengeController {

	@GetMapping(value = "{path}")
	public void getChallenge(@PathVariable("path") String challengeId) {
		System.out.println(challengeId);
	}

	@PostMapping
	public void createChallenge(@RequestBody Entry request) {
		System.out.println(request.name());
	}

	private record Entry(String name, String type) {
	}

}

