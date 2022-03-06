package org.challenger.challenger.domain;

// посылка, подача
public class Submission {
    public String submissionId;
    public Boolean type; // тип апррув

    public Submission(String submissionId, Boolean type) {
        this.submissionId = submissionId;
        this.type = type;
    }

}
