package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import entity.Offering;
import entity.Schedule;

public class ScheduleDao {
	
	private static ConnectionFactory connectionFactory = new ConnectionFactory();
	
	public static Connection getConnection() throws SQLException{
		Connection conn = connectionFactory.getConnection();
		return conn;
	}
	
	public static void closeConnection(Connection connection){
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void deleteAll() throws SQLException {
		Connection connection = getConnection();
		try {
			Statement statement = connection.createStatement();
			statement.executeUpdate("DELETE FROM schedule;");
		} 
		finally {
			closeConnection(connection);
		}
	}
	
	public static Schedule create(String name) throws SQLException {
		Connection connection = getConnection();
		try {
			Statement statement = connection.createStatement();
			statement.executeUpdate("DELETE FROM schedule WHERE Name = '" + name + "';");
			return new Schedule(name);
		} 
		finally {
			closeConnection(connection);
		}
	}
	
	public static Schedule find(String name) throws SQLException {
		Connection connection = getConnection();
		try {
			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery("SELECT * FROM schedule WHERE Name= '" + name + "';");
			Schedule schedule = new Schedule(name);
			while (result.next()) {
				int offeringId = result.getInt("OfferingId");
				Offering offering = OfferingDao.find(offeringId);
				schedule.getOfferings().add(offering);
			}
			return schedule;
		} 
		finally {
			closeConnection(connection);
		}
	}

	public static Collection<Schedule> all() throws SQLException {
		Connection connection = getConnection();
		ArrayList<Schedule> result = new ArrayList<Schedule>();
		try {
			Statement statement = connection.createStatement();
			ResultSet results = statement.executeQuery("SELECT DISTINCT Name FROM schedule;");
			while (results.next())
			result.add(find(results.getString("Name")));
		} 
		finally {
			closeConnection(connection);
		}
		return result;
	}

	public static void update(Schedule schedule) throws SQLException {
		Connection connection = getConnection();
		try {
			Statement statement = connection.createStatement();
			statement.executeUpdate("DELETE FROM schedule WHERE name = '" + schedule.getName() + "';");
			for (int i = 0; i < schedule.getOfferings().size(); i++) {
				Offering offering = (Offering) schedule.getOfferings().get(i);
				statement.executeUpdate("INSERT INTO schedule VALUES('" + schedule.getName() + "','" + offering.getId() + "');");
			}
		} 
		finally {
			closeConnection(connection);
		}
	}
		
}
