package org.challenger.challenger.domain;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SubmissionRepository {
	public List<Submission> getSubmissionByChallengeId(String challengeId) {
		List<Submission> result = new ArrayList<>();

		String sql = "Select * from Submissions where challengeid = ?";

		try (
			Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/challenger", "postgres", "D");
			PreparedStatement preparedStatement = conn.prepareStatement(sql);
		) {
			preparedStatement.setString(1, challengeId);
			ResultSet resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				Submission submission = new Submission(
					resultSet.getString("id"),
					resultSet.getString("user_id"),
					resultSet.getInt("value"));
				result.add(submission);
			}
			return result;
		} catch (SQLException e) {
			System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
			e.printStackTrace();
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public void createSubmissionByChallengeId(Submission submission, String challengeId) {

		String sql = "insert into submissions (id, challengeId, value, user_id) Values (?, ?, ?, ?)";

		try (
			Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/challenger", "postgres", "D");
			PreparedStatement preparedStatement = conn.prepareStatement(sql);
		) {
			preparedStatement.setString(1, submission.id());
			preparedStatement.setString(2, challengeId);
			preparedStatement.setInt(3, submission.value());
			preparedStatement.setString(4, submission.userId());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
