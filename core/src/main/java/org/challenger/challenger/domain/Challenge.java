package org.challenger.challenger.domain;


import lombok.*;
import org.challenger.challenger.infrastructure.controller.dto.ChallengeActivateDto;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Challenge {

    private String id;

    @Builder.Default
    private final ChallengeType type = ChallengeType.NUMERIC;
    private ChallengeState state;
    private Integer progress; // прогресс (91из100)
    private String name; // название
    private Integer goal; // цель (100)
    private String ownerUserId; // список участников (11участник)
    private List<String> userIds = new ArrayList<>();
    private String chatId;
    private List<Submission> submissions = new ArrayList<>();
    private String buttons;
// Domain driven design - 1. bounded contexts 2. 3 kinds of domains

    // добавить прогресс
    public void appendProgress(Integer submissionValue){
        this.progress += submissionValue;
        if (this.progress >= goal) {
            state = ChallengeState.FINISHED;
        }
    }

    @Override
    public String toString() {
        return """
            id: %s
            name: %s
            users: %s
            goal: %s
            progress: %s
            state: %s
            """.formatted(getId(), getName(), getUserIds(), getGoal(), getProgress(), getState());
    }
}
