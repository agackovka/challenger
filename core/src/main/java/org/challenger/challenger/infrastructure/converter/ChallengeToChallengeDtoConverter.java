package org.challenger.challenger.infrastructure.converter;

import org.challenger.challenger.domain.Challenge;
import org.challenger.challenger.domain.Submission;
import org.challenger.challenger.domain.UserRepository;
import org.challenger.challenger.infrastructure.controller.dto.ChallengeDto;
import org.challenger.challenger.infrastructure.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ChallengeToChallengeDtoConverter extends BaseConverter<Challenge, ChallengeDto> {

    @Autowired
    private UserService userService;

    @Override
    public ChallengeDto convert(Challenge challenge) {
        return new ChallengeDto(
            challenge.getId(),
            challenge.getName(),
            challenge.getType().name(),
            challenge.getState().name(),
            challenge.getGoal(),
            challenge.getProgress(),
            userService.calculateUserHandle(challenge.getOwnerUserId()),
            convertUserIds(challenge.getUserIds()),
            convertSubmissions(challenge.getSubmissions())
        );
    }

    private List<ChallengeDto.SubmissionDto> convertSubmissions(List<Submission> submissions) {
        return submissions.stream().map(s -> new ChallengeDto.SubmissionDto(
            userService.calculateUserHandle(s.userId()),
            s.value())
        ).toList();
    }

    private List<String> convertUserIds(List<String> userIds) {
        return userIds.stream().map(id -> userService.calculateUserHandle(id)).toList();
    }
}
