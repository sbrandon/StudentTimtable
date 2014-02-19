package entity;

import java.sql.*;

import studentTimetable.DatabaseFacade;

public class Course {
	private String name;
	private int credits;
	private static DatabaseFacade databaseFacade = new DatabaseFacade();
	
	public static Connection getConnection(){
		Connection conn = databaseFacade.getConnection();
		return conn;
	}
	
	public static void closeConnection(Connection connection){
		try {
			connection.close();
		} catch (SQLException e) {
			System.out.println(e);
		}
	}
	
	public static Course create(String name, int credits) throws Exception {
		Connection connection = getConnection();
		try {
			Statement statement = connection.createStatement();
			statement.executeUpdate("DELETE FROM course WHERE name = '" + name + "';");
			statement.executeUpdate("INSERT INTO course VALUES ('" + name + "', '" + credits + "');");
			return new Course(name, credits);
		} 
		finally {
			closeConnection(connection);
		}
	}

	public static Course find(String name) {
		Connection connection = getConnection();
		try {
			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery("SELECT * FROM course WHERE Name = '" + name + "';");
			if (!result.next()) return null;
			int credits = result.getInt("Credits");
			return new Course(name, credits);
		} 
		catch (Exception ex) {
			return null;
		} 
		finally {
			closeConnection(connection);
		}
	}
	
	public void update() throws Exception {
		Connection connection = getConnection();
		try {
			Statement statement = connection.createStatement();
			statement.executeUpdate("DELETE FROM COURSE WHERE name = '" + name + "';");
			statement.executeUpdate("INSERT INTO course VALUES('" + name + "','" + credits + "');");
		} 
		finally {
			closeConnection(connection);
		}
	}
	
	//Constructor
	public Course(String name, int credits) {
		this.name = name;
		this.credits = credits;
	}
	
	/*
	 * Getters and Setters
	 */
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCredits() {
		return credits;
	}

	public void setCredits(int credits) {
		this.credits = credits;
	}
	
	
}
