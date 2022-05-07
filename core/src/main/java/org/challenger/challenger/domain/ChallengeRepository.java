package org.challenger.challenger.domain;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class ChallengeRepository {
	public void create(Challenge challenge) {
		// TODO should be autowired with Spring
		UsersChallengesRepository usersChallengesRepository = new UsersChallengesRepository();

		String sql = "insert into challenges (id, progress, goal, ownuserid, chatid, name, type, state, buttons) Values (?, ?, ?, ?, ?, ?, ?, ?, ?)";
		String id = challenge.getId();
		int progress = challenge.getProgress();
		int goal = challenge.getGoal();
		String ownUser = challenge.getOwnerUserId();
		List<String> userIds = challenge.getUserIds();
		String chatId = challenge.getChatId();
		String name = challenge.getName();
		String type = challenge.getType().toString();
		String buttons = challenge.getButtons();

		try (
			Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/challenger", "postgres", "D");
			PreparedStatement preparedStatement = conn.prepareStatement(sql)
		) {

			preparedStatement.setString(1, id);
			preparedStatement.setInt(2, progress);
			preparedStatement.setInt(3, goal);
			preparedStatement.setString(4, ownUser);
			preparedStatement.setString(5, chatId);
			preparedStatement.setString(6, name);
			preparedStatement.setString(7, type);
			preparedStatement.setString(8, buttons);

			if (challenge.getState() != null) {
				preparedStatement.setString(8, challenge.getState().toString());
			}

			usersChallengesRepository.linkUsersWithChallenge(userIds, id);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Challenge getChallengeById(String challengeId) {
		String sql = "select * from challenges where id = ?";
		UsersChallengesRepository usersChallengesRepository = new UsersChallengesRepository();
		Properties properties = new Properties();
		properties.setProperty("user","postgres");
		properties.setProperty("password","D");
		properties.setProperty("useUnicode","true");
		properties.setProperty("characterEncoding","UTF8");

		Challenge result = new Challenge();

		try (
			Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/challenger", properties);
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
		) {
			preparedStatement.setString(1, challengeId);
			ResultSet resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				ChallengeState state = ChallengeState.valueOf(resultSet.getString("state"));
				ChallengeType type = ChallengeType.valueOf(resultSet.getString("type"));
				Challenge challenge = Challenge.builder()
					.id(challengeId)
					.progress(resultSet.getInt("progress"))
					.goal(resultSet.getInt("goal"))
					.ownerUserId(resultSet.getString("ownUserId"))
					.chatId(resultSet.getString("chatId"))
					.name(resultSet.getString("name"))
					.state(state)
					.type(type)
					.userIds(usersChallengesRepository.getUsersIds(challengeId, connection))
					.build();
				result = challenge;

			}
			return result;

		} catch (SQLException e) {
			String message = e.getMessage();
			System.err.format("SQL State: %s\n%s", e.getSQLState(), message);
			e.printStackTrace();
			return  null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<Challenge> getChallengesByChatId(String chatId) {
		UsersChallengesRepository usersChallengesRepository = new UsersChallengesRepository();
		List<Challenge> result = new ArrayList<>();

		String sql = "Select * from Challenges where chatid = ?";

		try (
			Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/challenger", "postgres", "D");
			PreparedStatement preparedStatement = conn.prepareStatement(sql);
		) {
			preparedStatement.setString(1, chatId);
			ResultSet resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				ChallengeState state = ChallengeState.valueOf(resultSet.getString("state"));
				ChallengeType type = ChallengeType.valueOf(resultSet.getString("type"));
				Challenge challenge = Challenge.builder()
					.id(resultSet.getString("id"))
					.progress(resultSet.getInt("progress"))
					.goal(resultSet.getInt("goal"))
					.ownerUserId(resultSet.getString("ownUserId"))
					.chatId(resultSet.getString("chatId"))
					.name(resultSet.getString("name"))
					.state(state)
					.type(type)
					.userIds(usersChallengesRepository.getUsersIds(resultSet.getString("id"), conn))
					.build();

				result.add(challenge);
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

	public Integer getChallengeProgress(String challengeId) {

		int result = 0;
		String sql = "select progress from challenges where id = ?";

		try (
			Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/challenger", "postgres", "D");
			PreparedStatement preparedStatement = conn.prepareStatement(sql);
		) {
			preparedStatement.setString(1, challengeId);
			ResultSet resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				result = resultSet.getInt("progress");
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

	public void updateChallengeProgress(int value, String challengeId) {

		String sql = "update challenges set progress = ? where id = ?";
		String sqlGetProgress = "select progress from challenges where id = ?";

		try (
			Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/challenger", "postgres", "D");
			PreparedStatement preparedStatementGetProgress = conn.prepareStatement(sqlGetProgress);
		) {
			preparedStatementGetProgress.setString(1, challengeId);
			ResultSet resultSet = preparedStatementGetProgress.executeQuery();
			int finalValue = value;
			while (resultSet.next()) {
				finalValue = finalValue + resultSet.getInt("progress");
			}

			PreparedStatement preparedStatementUpdateProgress = conn.prepareStatement(sql);
			preparedStatementUpdateProgress.setInt(1, finalValue);
			preparedStatementUpdateProgress.setString(2, challengeId);

			preparedStatementUpdateProgress.executeUpdate();
		} catch (SQLException e) {
			System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void updateChallengeState(String challengeId, ChallengeState challengeState) {
		String sql = "update challenges set state = ? where id = ?";

		try (
			Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/challenger", "postgres", "D");
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			)
		{
			preparedStatement.setString(1, challengeState.name());
			preparedStatement.setString(2, challengeId);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
