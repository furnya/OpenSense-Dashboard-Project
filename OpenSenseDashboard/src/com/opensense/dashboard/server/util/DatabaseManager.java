package com.opensense.dashboard.server.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.mysql.cj.jdbc.MysqlDataSource;

public class DatabaseManager {

	private static final Logger logger = Logger.getLogger(DatabaseManager.class.getName());

	private static MysqlDataSource dataSource = new MysqlDataSource();

	public void initPooling() {
		dataSource.setUser(System.getenv("MYSQL_USERNAME"));
		dataSource.setPassword(System.getenv("MYSQL_PASSWORD"));
		dataSource.setServerName(System.getenv("MYSQL_HOST"));
		dataSource.setPort(Integer.valueOf(System.getenv("MYSQL_PORT")));
		dataSource.setDatabaseName("dashboard");
	}

	/**
	 * opens the connection to the MYSQL database
	 */
	private static Connection openSQLConnection() {
		try {
			Connection sqlCon;
			sqlCon = dataSource.getConnection();
			return sqlCon;
		} catch (SQLException e) {
			logger.log(Level.WARNING, ServerLanguages.unexpectedErrorLog(), e);
			return null;
		}
	}

	public Boolean testDB(int id, String name) {
		String sql = "INSERT INTO Test (id, name) VALUES (?, ?);";
		int result;
		try (Connection con = openSQLConnection(); PreparedStatement statement = con.prepareStatement(sql)){
			statement.setInt(1, id);
			statement.setString(2, name);
			result = statement.executeUpdate();
		} catch (Exception e) {
			logger.log(Level.WARNING, ServerLanguages.unexpectedErrorLog(), e);
			result = 0;
		}
		return result != 0;
	}
}
