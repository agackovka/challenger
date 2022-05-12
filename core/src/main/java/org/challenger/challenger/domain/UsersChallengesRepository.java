package org.challenger.challenger.domain;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public interface UsersChallengesRepository {
	public List<UsersChallenges> getUsersIds(String challengeId);
//	{
//		List<String> result = new ArrayList<>();
//
//		String sql = "Select userid from users_challenges where challengeid = ?";
//
//		try (
//			PreparedStatement preparedStatement = connection.prepareStatement(sql);
//		) {
//			preparedStatement.setString(1, challengeId);
//			ResultSet resultSet = preparedStatement.executeQuery();
//
//			while (resultSet.next()) {
//				result.add(resultSet.getString("userId"));// in java - userId, in database - user_id or USER_ID
//			}
//			return result;
//
//		} catch (SQLException e) {
//			System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
//			e.printStackTrace();
//			return null;
//		} catch (Exception e) {
//			e.printStackTrace();
//			return null;
//		}
//	}

	public void linkUsersWithChallenge(List<String> ids, String challengeid);
//	{
//		String sql = "insert into users_challenges (userid, challengeid) Values (?, ?)";
//
//		Properties properties = new Properties();
//		properties.setProperty("user","postgres");
//		properties.setProperty("password","D");
//		properties.setProperty("useUnicode","true");
//		properties.setProperty("characterEncoding","UTF8");
//		try (
//			Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/challenger", properties);
//			PreparedStatement preparedStatement = connection.prepareStatement(sql);
//		) {
//
////			ids.forEach(s -> {
////				preparedStatement.setString(1, s);
////				preparedStatement.setString(2, challengeid);
////				preparedStatement.executeUpdate();
////			});
//
//			for (String s : ids) {
//				preparedStatement.setString(1, s);
//				preparedStatement.setString(2, challengeid);
//				preparedStatement.executeUpdate();
//			}
//
//		} catch (SQLException e) {
//			System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
//			throw new RuntimeException(e); // differences between RuntimeException and Exception (checked vs unchecked exceptions)
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}

}
