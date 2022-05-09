package org.challenger.challenger.domain;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public interface SubmissionRepository {
	public List<Submission> getSubmissionByChallengeId(String challengeId);

	public void createSubmissionByChallengeId(Submission submission, String challengeId);
}
