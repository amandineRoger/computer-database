package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SingleConnect {
	// Constants
	private static final String ADRESS = "jdbc:mysql://127.0.0.1:3306/computer-database-db?zeroDateTimeBehavior=convertToNull";
	private static final String USERNAME = "admincdb";
	private static final String PASSWORD = "qwerty1234";

	private static final String LOG_TAG = "SingleConnect says : ";

	// Attributes
	private Connection connect;
	private StringBuffer sb;

	// Getters
	/**
	 * Open db connection, you must to close it later !
	 * 
	 * @return java.sql.Connection
	 */
	public Connection getConnection() {
		try {
			this.connect = DriverManager.getConnection(ADRESS, USERNAME, PASSWORD);
		} catch (SQLException e) {
			sb.append(" SQLException ");
			sb.append(e.getMessage());
			System.out.println(sb);
			resetStringBuffer();
		}
		return this.connect;
	}

	// Singleton
	private static SingleConnect instance = null;

	private SingleConnect() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			sb = new StringBuffer(LOG_TAG);
			sb.append(" ClassNotFoundException ! ");
			sb.append(e.getMessage());
			System.out.println(sb);
			resetStringBuffer();
		}
	}

	/**
	 * GetInstance, singleton pattern
	 * 
	 * @return the unique instance of SingleConnection
	 */
	public static SingleConnect getInstance() {
		if (instance == null) {
			instance = new SingleConnect();
		}
		return instance;
	}

	// Methods
	private void resetStringBuffer() {
		sb.delete(LOG_TAG.length(), sb.length());
	}

}
