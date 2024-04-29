package sait.mms.drivers;

import java.sql.*;

import sait.mms.contracts.DatabaseDriver;

public class MariaDBDriver implements DatabaseDriver {
	private static final String SERVER = "localhost";
	private static final int PORT = 3306;
	private static final String DATABASE = "cprg251";
	private static final String USERNAME = "root";
	private static final String PASSWORD = "password";
	
	Connection connection = null;

	@Override
	public void connect() throws SQLException {
		String dsn = this.getDsn();
		connection = DriverManager.getConnection(dsn);
		
	}

	/**
	 * Formats the DSN string with the given server, port, databse, username, and password
	 * @return The formatted Data Source Name (DSN) string
	 */
	private String getDsn() {
		String dataSourceName = String.format("jdbc:mariadb://%s:%d/%s?user=%s&password=%s", SERVER, PORT, DATABASE, USERNAME, PASSWORD);
		
		return dataSourceName;
	}

	@Override
	public ResultSet get(String query) throws SQLException {
		Statement statement = connection.createStatement();
		ResultSet results = statement.executeQuery(query);
		
		return results;
	}

	@Override
	public int update(String query) throws SQLException {
		Statement statement = connection.createStatement();
		int rows = statement.executeUpdate(query);
		return rows;
	}

	@Override
	public void disconnect() throws SQLException {
		if (connection != null && !connection.isClosed()) {
			connection.close();
		}
		
	}

}
