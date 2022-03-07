package org.challenger.challenger.domain;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

// челенж
@Getter
@Builder
@AllArgsConstructor
public class Challenge {

    private String id;

    private final ChallengeType type = ChallengeType.NUMERIC; // тип числовой (1из100)

    private Integer progress; // прогресс (91из100)
    private String name; // название
    private Integer goal; // цель (100)
    private String ownerUserId; // список участников (11участник)
    private List<String> userIds;
    private List<Submission> submissions;

    // изменить прогресс
    public void appendProgress(Integer submissionValue){
        this.progress += submissionValue;
    }







}
