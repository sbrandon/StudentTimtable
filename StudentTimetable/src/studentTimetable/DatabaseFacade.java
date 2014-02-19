package studentTimetable;

import java.sql.DriverManager;

import com.mysql.jdbc.Connection;

public class DatabaseFacade {
	
	private static String databaseUrl = "jdbc:mysql://127.0.0.1:3306/studenttimetable";
	private String username = "root";
	private String password = "";
	
	public DatabaseFacade(){
		
	}
	
	public Connection getConnection(){
		Connection connection = null;
		try{
			Class.forName("com.mysql.jdbc.Driver");
			connection = (Connection) DriverManager.getConnection(databaseUrl, username, password);
		}
		catch(Exception e){
			
		}
		return connection;
	}
	
}
