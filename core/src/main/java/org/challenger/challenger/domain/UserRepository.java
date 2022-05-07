package org.challenger.challenger.domain;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class UserRepository {
	public void addUser (String id, String name, String firstName, String lastName) {
		String addUser = "insert into user (id, name, first_name, last_name) Values (?, ?, ?, ?)";
		String checkUser = "select id from users";
		List<String> result = new ArrayList<>();
		try (
			Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/challenger", "postgres", "D");
			PreparedStatement preparedStatementCheckUserId = conn.prepareStatement(checkUser);
		) {
			ResultSet resultSet = preparedStatementCheckUserId.executeQuery();

			while (resultSet.next()) {
				result.add(resultSet.getString("id"));
			}

			PreparedStatement preparedStatementAddUser = conn.prepareStatement(addUser);
			for (String s : result) {
				if (Objects.equals(s, id)) {
					preparedStatementAddUser.setString(1, id);
					preparedStatementAddUser.setString(2, name);
					preparedStatementAddUser.setString(3, firstName);
					preparedStatementAddUser.setString(4, lastName);

					preparedStatementAddUser.executeUpdate();
				}
			}

		} catch (SQLException e) {
			System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
