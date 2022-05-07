package org.challenger.challenger.domain;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BD {
	public static void main(String[] args) {

		List<Challenge> result = new ArrayList<>();

		String SQL_SELECT = "Select * from Challenges";

		try (
			Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/challenger", "postgres", "D");
		    PreparedStatement preparedStatement = conn.prepareStatement(SQL_SELECT);
		) {

			ResultSet resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				String id = resultSet.getString("ID");
				String name = resultSet.getString("NAME");

				Challenge obj = new Challenge();
				obj.setId(id);
				obj.setName(name);

				result.add(obj);

			}
			result.forEach(System.out::println);

		} catch (SQLException e) {
			System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
