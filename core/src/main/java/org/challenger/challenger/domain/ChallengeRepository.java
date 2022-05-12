package org.challenger.challenger.domain;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public interface ChallengeRepository {
	public void create(Challenge challenge);

	public Challenge getChallengeById(String challengeId);

	public List<Challenge> getChallengesByChatId(String chatId);

	public Integer getChallengeProgress(String challengeId);
//
//		int result = 0;
//		String sql = "select progress from challenges where id = ?";
//
//		try (
//			Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/challenger", "postgres", "D");
//			PreparedStatement preparedStatement = conn.prepareStatement(sql);
//		) {
//			preparedStatement.setString(1, challengeId);
//			ResultSet resultSet = preparedStatement.executeQuery();
//
//			while (resultSet.next()) {
//				result = resultSet.getInt("progress");
//			}
//			return result;
//		} catch (SQLException e) {
//			System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
//			e.printStackTrace();
//			return null;
//		} catch (Exception e) {
//			e.printStackTrace();
//			return null;
//		}
//	}

	public void updateChallengeProgress(int value, String challengeId);
//
//		String sql = "update challenges set progress = ? where id = ?";
//		String sqlGetProgress = "select progress from challenges where id = ?";
//
//		try (
//			Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/challenger", "postgres", "D");
//			PreparedStatement preparedStatementGetProgress = conn.prepareStatement(sqlGetProgress);
//		) {
//			preparedStatementGetProgress.setString(1, challengeId);
//			ResultSet resultSet = preparedStatementGetProgress.executeQuery();
//			int finalValue = value;
//			while (resultSet.next()) {
//				finalValue = finalValue + resultSet.getInt("progress");
//			}
//
//			PreparedStatement preparedStatementUpdateProgress = conn.prepareStatement(sql);
//			preparedStatementUpdateProgress.setInt(1, finalValue);
//			preparedStatementUpdateProgress.setString(2, challengeId);
//
//			preparedStatementUpdateProgress.executeUpdate();
//		} catch (SQLException e) {
//			System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
//			e.printStackTrace();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}

	public void updateChallengeState(String challengeId, ChallengeState challengeState);
//		String sql = "update challenges set state = ? where id = ?";
//
//		try (
//			Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/challenger", "postgres", "D");
//			PreparedStatement preparedStatement = connection.prepareStatement(sql);
//			)
//		{
//			preparedStatement.setString(1, challengeState.name());
//			preparedStatement.setString(2, challengeId);
//			preparedStatement.executeUpdate();
//		} catch (SQLException e) {
//			System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
//			e.printStackTrace();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
}
