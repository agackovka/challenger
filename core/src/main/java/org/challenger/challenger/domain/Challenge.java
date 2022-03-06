package org.challenger.challenger.domain;


import java.util.ArrayList;
import java.util.List;

// челенж
public class Challenge {

    public String id;
    public String name; // название
    public String type; // тип числовой (1из100)
    public Integer goal; // цель (100)
    public Integer progress; // прогресс (91из100)
    public Integer userId; // список участников (11участник)
    public String userEmail;
    public Integer submissionId; // список посылок (91 посылка) //длолжен быть не один id

    private final List<Submission> listSubmission = new ArrayList<>(3);

    public Challenge(String id, String name, String type, Integer goal, Integer progress, Integer userId, String userEmail, Integer submissionId) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.goal = goal;
        this.progress = progress;
        this.userId = userId;
        this.userEmail = userEmail;
        this.submissionId = submissionId;
    }

    public void changeProgress() {
        this.progress += 1;
    }

    public void applySubmission(Submission submission) {
        listSubmission.add(submission);
    }

}
