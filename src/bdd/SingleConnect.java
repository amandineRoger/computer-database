package bdd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SingleConnect {
	//Constants
	private static final String ADRESS = "jdbc:mysql://127.0.0.1:3306/computer-database-db?zeroDateTimeBehavior=convertToNull";
	private static final String USERNAME = "admincdb";
	private static final String PASSWORD = "qwerty1234";
	
	private static final String LOG_TAG = "SingleConnect says : ";
	
	//Attributes
	private Connection connect;
	private StringBuffer sb;
	
	//Getters
	public Connection getConnection(){
		try {
			this.connect = DriverManager.getConnection(ADRESS, USERNAME, PASSWORD);
		} catch (SQLException e) {
			sb.append(" SQLException ");
			sb.append(e.getMessage());
			System.out.println( sb );
			resetStringBuffer();
		}
		
		return this.connect;
	}
	
	
	//Singleton
	private static SingleConnect instance = null;
	
	private SingleConnect(){
		try {
			Class.forName("com.mysql.jdbc.Driver");
			sb = new StringBuffer(LOG_TAG);
		} catch (ClassNotFoundException e) {
			sb.append(" ClassNotFoundException ! ");
			sb.append(e.getMessage());
			System.out.println( sb );
			resetStringBuffer();
		}
	}
	
	public static SingleConnect getInstance(){
		if (instance == null) {
			instance = new SingleConnect();
		}
		return instance;
	}
	
	
	//Methods
	private void resetStringBuffer(){
		sb.delete(LOG_TAG.length(), sb.length());
	}
	
	
}
