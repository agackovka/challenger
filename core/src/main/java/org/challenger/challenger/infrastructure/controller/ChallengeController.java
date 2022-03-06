package org.challenger.challenger.infrastructure.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v1/challenges")
@RestController
public class ChallengeController {

	@GetMapping(value = "{path}")
	public void getChallenge(@PathVariable("path") String challengeId) {

	}

}
