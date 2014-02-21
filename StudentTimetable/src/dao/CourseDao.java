/*
 * CourseDAO.java
 * Manages interaction with the course table in the database.
 */
package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import entity.Course;

public class CourseDao{

	private static ConnectionFactory connectionFactory = new ConnectionFactory();
	
	public static Connection getConnection() throws SQLException{
		Connection conn = connectionFactory.getConnection();
		return conn;
	}
	
	public static void closeConnection(Connection connection){
		try {
			connection.close();
		} catch (SQLException e) {
			System.out.println(e);
		}
	}
	
	public static Object create(Object object) throws SQLException {
		Course course = (Course) object;
		Connection connection = getConnection();
		try {
			Statement statement = connection.createStatement();
			statement.executeUpdate("DELETE FROM course WHERE name = '" + course.getName() + "';");
			statement.executeUpdate("INSERT INTO course VALUES ('" + course.getName() + "', '" + course.getCredits() + "');");
			return course;
		}
		finally {
			closeConnection(connection);
		}
	}

	public static Course find(Object object) throws SQLException {
		Course course = (Course) object;
		Connection connection = getConnection();
		try {
			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery("SELECT * FROM course WHERE Name = '" + course.getName() + "';");
			if (!result.next()) return null;
			int credits = result.getInt("Credits");
			return course;
		} 
		finally {
			closeConnection(connection);
		}
	}
	
	public static void update(Object object) throws SQLException {
		Course course = (Course) object;
		Connection connection = getConnection();
		try {
			Statement statement = connection.createStatement();
			statement.executeUpdate("DELETE FROM COURSE WHERE name = '" + course.getName() + "';");
			statement.executeUpdate("INSERT INTO course VALUES('" + course.getName() + "','" + course.getCredits() + "');");
		} 
		finally {
			closeConnection(connection);
		}
	}
	
}
