package org.challenger.challenger.infrastructure.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.challenger.challenger.infrastructure.controller.dto.SubmitRequest;
import org.challenger.challenger.infrastructure.service.SubmissionService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/submissions")
public class SubmissionController {

	private final SubmissionService submissionService;

	@PostMapping
	public void submit(@RequestBody SubmitRequest request) {
		log.info("submit.enter; challengeId={} value={}", request.challengeId(), request.value());
		submissionService.submit(request.challengeId(), request.value());
	}

}
