package org.challenger.challenger.domain;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

// челенж
@Getter
@Builder
@Setter
@AllArgsConstructor
public class Challenge {

    private String id;

    private final ChallengeType type = ChallengeType.NUMERIC; // тип числовой (1из100)

    private ChallengeState state;

    private Integer progress; // прогресс (91из100)
    private String name; // название
    private Integer goal; // цель (100)
    private String ownerUserId; // список участников (11участник)
    private List<String> userIds;
    private String chatId;
    private List<Submission> submissions;

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
            name: %s
            users: %s
            goal: %s
            progress: %s
            state: %s
            """.formatted(getName(), getUserIds(), getGoal(), getProgress(), getState());
    }
}
