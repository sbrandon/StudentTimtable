/*
 * ConnectionFactory.java
 * Manages database connections.
 */
package dao;

import java.sql.DriverManager;
import java.sql.SQLException;

import com.mysql.jdbc.Connection;

public class ConnectionFactory {
	
	private static String databaseUrl = "jdbc:mysql://127.0.0.1:3306/studenttimetable";
	private String driverClass = "com.mysql.jdbc.Driver";
	private String username = "root";
	private String password = "";
	
	public ConnectionFactory(){
		try{
			Class.forName(driverClass);	
		}
		catch(ClassNotFoundException e){
			e.printStackTrace();
		}
	}
	
	//Returns a connection instance
	public Connection getConnection() throws SQLException{
		Connection conn = null;
		conn = (Connection) DriverManager.getConnection(databaseUrl, username, password);
		return conn;
	}
	
}
