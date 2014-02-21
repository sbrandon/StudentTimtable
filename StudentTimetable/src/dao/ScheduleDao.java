package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import entity.Course;
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
			System.out.println(e);
		}
	}
	
	public static void deleteAll() throws Exception {
		Connection connection = getConnection();
		try {
			Statement statement = connection.createStatement();
			statement.executeUpdate("DELETE FROM schedule;");
		} 
		finally {
			closeConnection(connection);
		}
	}
	
	public static Schedule create(String name) throws Exception {
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
				//schedule.add(offering);
			}
			return schedule;
		} 
		catch (Exception ex) {
			return null;
		} 
		finally {
			closeConnection(connection);
		}
	}

	public static Collection<Schedule> all() throws Exception {
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

	public void update(String name, ArrayList<Offering> schedule) throws Exception {
		Connection connection = getConnection();
		try {
			Statement statement = connection.createStatement();
			statement.executeUpdate("DELETE FROM schedule WHERE name = '" + name + "';");
			for (int i = 0; i < schedule.size(); i++) {
				Offering offering = (Offering) schedule.get(i);
				statement.executeUpdate("INSERT INTO schedule VALUES('" + name + "','" + offering.getId() + "');");
			}
		} 
		finally {
			closeConnection(connection);
		}
	}
		
}
