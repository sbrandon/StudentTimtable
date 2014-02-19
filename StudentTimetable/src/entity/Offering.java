package entity;

import java.sql.*;

import studentTimetable.DatabaseFacade;

public class Offering {
	private int id;
	private Course course;
	private String daysTimes;
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
	
	public static Offering create(Course course, String daysTimesCsv) throws Exception {
		Connection connection = getConnection();
		try {
			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery("SELECT MAX(ID) FROM offering;");
			result.next();
			int newId = 1 + result.getInt(1);
			statement.executeUpdate("INSERT INTO offering VALUES ('"+ newId + "','" + course.getName() + "','" + daysTimesCsv + "');");
			return new Offering(newId, course, daysTimesCsv);
		} 
		finally {
			closeConnection(connection);
		}
	}

	public static Offering find(int id) {
		Connection connection = getConnection();
		try {
			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery("SELECT * FROM offering WHERE ID =" + id + ";");
			if (result.next() == false)
				return null;
			String courseName = result.getString("Name");
			Course course = Course.find(courseName);
			String dateTime = result.getString("DaysTimes");
			connection.close();
			return new Offering(id, course, dateTime);
		} 
		catch (Exception ex) {
			closeConnection(connection);
			return null;
		}
	}
	
	public void update() throws Exception {
		Connection connection = getConnection();
		try {
			Statement statement = connection.createStatement();
			statement.executeUpdate("DELETE FROM Offering WHERE ID=" + id + ";");
			statement.executeUpdate("INSERT INTO Offering VALUES('" + id + "','" + course.getName() + "','" + daysTimes + "');");
		} 
		finally {
			closeConnection(connection);
		}
	}
	
	//Constructor
	public Offering(int id, Course course, String daysTimesCsv) {
		this.id = id;
		this.course = course;
		this.daysTimes = daysTimesCsv;
	}

	public String toString() {
		return "Offering " + getId() + ": " + getCourse() + " meeting " + getDaysTimes();
	}
	
	/*
	 * Getters and Setters
	 */
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public String getDaysTimes() {
		return daysTimes;
	}

	public void setDaysTimes(String daysTimes) {
		this.daysTimes = daysTimes;
	}
}
