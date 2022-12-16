package com.mindtree.dao;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DataBaseConnection {
	private Connection connection;

	public DataBaseConnection() throws SQLException, IOException {
		setConnection(connection());
	}

	private Connection connection() throws SQLException, IOException {
		Properties properties = getProperties();
		return DriverManager.getConnection(properties.getProperty("url"), properties.getProperty("user"),
				properties.getProperty("password"));

	}

	private Properties getProperties() throws IOException {
		FileReader reader = new FileReader(
				"src\\main\\resources\\db.properties");
		Properties p = new Properties();
		p.load(reader);
		return p;
	}

	public Connection getConnection() {
		return connection;
	}

	private void setConnection(Connection connection) {
		this.connection = connection;
	}

}
