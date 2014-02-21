package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import entity.Course;
import entity.Offering;

public class OfferingDao {

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
	
	public static Offering create(Course course, String daysTimesCsv) throws SQLException {
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

	public static Offering find(int id) throws SQLException {
		Connection connection = getConnection();
		try {
			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery("SELECT * FROM offering WHERE ID =" + id + ";");
			if (result.next() == false)
				return null;
			String courseName = result.getString("Name");
			Course course = CourseDao.find(courseName);
			String dateTime = result.getString("DaysTimes");
			connection.close();
			return new Offering(id, course, dateTime);
		} 
		finally{
			closeConnection(connection);
		}
	}
	
	public static void update(Offering offering, Course course) throws SQLException {
		Connection connection = getConnection();
		try {
			Statement statement = connection.createStatement();
			statement.executeUpdate("DELETE FROM Offering WHERE ID=" + offering.getId() + ";");
			statement.executeUpdate("INSERT INTO Offering VALUES('" + offering.getId() + "','" + course.getName() + "','" + offering.getDaysTimes() + "');");
		} 
		finally {
			closeConnection(connection);
		}
	}
	
}
