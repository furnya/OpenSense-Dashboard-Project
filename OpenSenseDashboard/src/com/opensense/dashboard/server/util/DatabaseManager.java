package com.opensense.dashboard.server.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.mysql.cj.jdbc.MysqlDataSource;
import com.opensense.dashboard.shared.ActionResult;
import com.opensense.dashboard.shared.ActionResultType;
import com.opensense.dashboard.shared.UserList;

public class DatabaseManager {

	private static final Logger logger = Logger.getLogger(DatabaseManager.class.getName());

	private static MysqlDataSource dataSource = new MysqlDataSource();

	public static void initPooling() {
		dataSource.setUser(System.getenv("MYSQL_USERNAME"));
		dataSource.setPassword(System.getenv("MYSQL_PASSWORD"));
		dataSource.setServerName(System.getenv("MYSQL_HOST"));
		dataSource.setPort(Integer.valueOf(System.getenv("MYSQL_PORT")));
		dataSource.setDatabaseName("dashboard");
	}

	public static void clearDataSource() {
		dataSource = new MysqlDataSource();
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

	public List<UserList> getUserLists(int userId) {
		String sql = "SELECT list_id, list_name, list_sensors FROM user_lists WHERE user_id = ?;";
		List<UserList> userLists = new ArrayList<>();
		ResultSet result = null;
		try (Connection con = openSQLConnection(); PreparedStatement statement = con.prepareStatement(sql)){
			statement.setInt(1, userId);
			result = statement.executeQuery();
			while(result.next()) {
				UserList list = new UserList();
				list.setListId(result.getInt("list_id"));
				list.setListName(result.getString("list_name"));
				String sensorsAsString = result.getString("list_sensors");
				list.setSensorIds(new SensorArrayFormatter().asArray(sensorsAsString));
				userLists.add(list);
			}
		} catch (Exception e) {
			logger.log(Level.WARNING, ServerLanguages.unexpectedErrorLog(), e);
		}
		return userLists;
	}

	public Integer createNewUserList(int userId) {
		String sql = "INSERT INTO user_lists (user_id, list_name, list_sensors) VALUES (?, ?, '[]');";
		Integer result = null;
		try (Connection con = openSQLConnection(); PreparedStatement statement = con.prepareStatement(sql)){
			statement.setInt(1, userId);
			statement.setString(2, ServerLanguages.newList());
			result = statement.executeUpdate();
			if((result != null) && (result != 0)) {
				ResultSet resultSet;
				sql = "SELECT MAX(list_id) AS list_id FROM user_lists WHERE user_id = ?;";
				try(PreparedStatement selectStatement = con.prepareStatement(sql)){
					selectStatement.setInt(1, userId);
					resultSet = selectStatement.executeQuery();
					while (resultSet.next()) {
						result = resultSet.getInt("list_id");
					}
					resultSet.close();
				}
			}else {
				return null;
			}
		} catch (Exception e) {
			logger.log(Level.WARNING, ServerLanguages.unexpectedErrorLog(), e);
			return null;
		}
		return result;
	}

	public ActionResult changeUserListName(int userId, int listId, String newListName) {
		String sql = "UPDATE user_lists SET list_name = ? WHERE user_id = ? AND list_id = ?;";
		Integer result = null;
		try (Connection con = openSQLConnection(); PreparedStatement statement = con.prepareStatement(sql)){
			statement.setString(1, newListName);
			statement.setInt(2, userId);
			statement.setInt(3, listId);
			result = statement.executeUpdate();
		} catch (Exception e) {
			logger.log(Level.WARNING, ServerLanguages.unexpectedErrorLog(), e);
			return new ActionResult(ActionResultType.FAILED);
		}
		return new ActionResult((result != null) && (result != 0) ? ActionResultType.SUCCESSFUL : ActionResultType.FAILED);
	}

	public ActionResult deleteUserList(int userId, int listId) {
		String sql = "DELETE FROM user_lists WHERE user_id = ? AND list_id = ?;";
		Integer result = null;
		try (Connection con = openSQLConnection(); PreparedStatement statement = con.prepareStatement(sql)){
			statement.setInt(1, userId);
			statement.setInt(2, listId);
			result = statement.executeUpdate();
		} catch (Exception e) {
			logger.log(Level.WARNING, ServerLanguages.unexpectedErrorLog(), e);
			return new ActionResult(ActionResultType.FAILED);
		}
		return new ActionResult((result != null) && (result != 0) ? ActionResultType.SUCCESSFUL : ActionResultType.FAILED);
	}

	public ActionResult addSensorsToUserList(int userId, int listId, List<Integer> sensors) {
		String sql = "SELECT list_sensors FROM user_lists WHERE user_id = ? AND list_id = ?;";
		ResultSet resultSet = null;
		Integer result = null;
		try (Connection con = openSQLConnection(); PreparedStatement selectStatement = con.prepareStatement(sql)){
			selectStatement.setInt(1, userId);
			selectStatement.setInt(2, listId);
			resultSet = selectStatement.executeQuery();
			String sensorsAsString = "";
			while (resultSet.next()) {
				sensorsAsString = resultSet.getString("list_sensors");
			}
			ArrayList<Integer> sensorsAsArray = (ArrayList<Integer>) new SensorArrayFormatter().asArray(sensorsAsString);
			sensorsAsArray.addAll(sensors);
			sql = "UPDATE user_lists SET list_sensors = ? WHERE user_id = ? AND list_id = ?;";
			try (PreparedStatement statement = con.prepareStatement(sql)){
				statement.setString(1, new SensorArrayFormatter().asString(sensorsAsArray));
				statement.setInt(2, userId);
				statement.setInt(3, listId);
				result = statement.executeUpdate();
			}
		}catch (Exception e) {
			logger.log(Level.WARNING, ServerLanguages.unexpectedErrorLog(), e);
			return new ActionResult(ActionResultType.FAILED);
		}
		return new ActionResult((result != null) && (result != 0) ? ActionResultType.SUCCESSFUL : ActionResultType.FAILED);
	}

	public ActionResult deleteSensorsFromUserList(Integer userId, int listId, List<Integer> sensors) {
		String sql = "SELECT list_sensors FROM user_lists WHERE user_id = ? AND list_id = ?;";
		ResultSet resultSet = null;
		Integer result = null;
		try (Connection con = openSQLConnection(); PreparedStatement selectStatement = con.prepareStatement(sql)){
			selectStatement.setInt(1, userId);
			selectStatement.setInt(2, listId);
			resultSet = selectStatement.executeQuery();
			String sensorsAsString = "";
			while (resultSet.next()) {
				sensorsAsString = resultSet.getString("list_sensors");
			}
			ArrayList<Integer> sensorsAsArray = (ArrayList<Integer>) new SensorArrayFormatter().asArray(sensorsAsString);
			sensorsAsArray.removeAll(sensors);
			sql = "UPDATE user_lists SET list_sensors = ? WHERE user_id = ? AND list_id = ?;";
			try (PreparedStatement statement = con.prepareStatement(sql)){
				statement.setString(1, new SensorArrayFormatter().asString(sensorsAsArray));
				statement.setInt(2, userId);
				statement.setInt(3, listId);
				result = statement.executeUpdate();
			}
		}catch (Exception e) {
			logger.log(Level.WARNING, ServerLanguages.unexpectedErrorLog(), e);
			return new ActionResult(ActionResultType.FAILED);
		}
		return new ActionResult((result != null) && (result != 0) ? ActionResultType.SUCCESSFUL : ActionResultType.FAILED);
	}
}
