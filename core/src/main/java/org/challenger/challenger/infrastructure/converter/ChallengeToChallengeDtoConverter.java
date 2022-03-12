package org.challenger.challenger.infrastructure.converter;

import org.challenger.challenger.domain.Challenge;
import org.challenger.challenger.domain.Submission;
import org.challenger.challenger.infrastructure.controller.dto.ChallengeDto;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ChallengeToChallengeDtoConverter extends BaseConverter<Challenge, ChallengeDto> {
    @Override
    public ChallengeDto convert(Challenge challenge) {
        return new ChallengeDto(
            challenge.getId(),
            challenge.getName(),
            challenge.getType().name(),
            challenge.getState().name(),
            challenge.getGoal(),
            challenge.getProgress(),
            new ChallengeDto.UserDto(challenge.getOwnerUserId()),
            convertUserIds(challenge.getUserIds()),
            convertSubmissions(challenge.getSubmissions())
        );
    }

    private List<ChallengeDto.SubmissionDto> convertSubmissions(List<Submission> submissions) {
        return submissions.stream().map(s -> new ChallengeDto.SubmissionDto(s.id(), s.userId(), s.value())).toList();
    }

    private List<ChallengeDto.UserDto> convertUserIds(List<String> userIds) {
        return userIds.stream().map(ChallengeDto.UserDto::new).toList();
    }
}
