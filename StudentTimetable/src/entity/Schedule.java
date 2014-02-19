package entity;

import java.util.*;
import java.sql.*;

import studentTimetable.DatabaseFacade;

public class Schedule {
	
	private String name;
	private int credits = 0;
	private static final int minCredits = 12;
	private static final int maxCredits = 18;
	private boolean permission = false;
	private ArrayList<Offering> schedule = new ArrayList<Offering>();
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
	
	public static Schedule find(String name) {
		Connection connection = getConnection();
		try {
			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery("SELECT * FROM schedule WHERE Name= '" + name + "';");
			Schedule schedule = new Schedule(name);
			while (result.next()) {
				int offeringId = result.getInt("OfferingId");
				Offering offering = Offering.find(offeringId);
				schedule.add(offering);
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
			result.add(Schedule.find(results.getString("Name")));
		} 
		finally {
			closeConnection(connection);
		}
		return result;
	}

	public void update() throws Exception {
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

	public Schedule(String name) {
		this.name = name;
	}

	public void add(Offering offering) {
		credits += offering.getCourse().getCredits();
		schedule.add(offering);
	}

	public void authorizeOverload(boolean authorized) {
		permission = authorized;
	}

	public List<String> analysis() {
		ArrayList<String> result = new ArrayList<String>();
		if (credits < minCredits)
			result.add("Too few credits");
		if (credits > maxCredits && !permission)
			result.add("Too many credits");
		checkDuplicateCourses(result);
		checkOverlap(result);
		return result;
	}

	public void checkDuplicateCourses(ArrayList<String> analysis) {
		HashSet<Course> courses = new HashSet<Course>();
		for (int i = 0; i < schedule.size(); i++) {
			Course course = ((Offering) schedule.get(i)).getCourse();
			if (courses.contains(course))
				analysis.add("Same course twice - " + course.getName());
			courses.add(course);
		}
	}

	public void checkOverlap(ArrayList<String> analysis) {
		HashSet<String> times = new HashSet<String>();
		for (Iterator<Offering> iterator = schedule.iterator(); iterator.hasNext();) {
			Offering offering = (Offering) iterator.next();
			String daysTimes = offering.getDaysTimes();
			StringTokenizer tokens = new StringTokenizer(daysTimes, ",");
			while (tokens.hasMoreTokens()) {
				String dayTime = tokens.nextToken();
				if (times.contains(dayTime))
					analysis.add("Course overlap - " + dayTime);
				times.add(dayTime);
			}
		}
	}

	public String toString() {
		return "Schedule " + name + ": " + schedule;
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

	public boolean isPermission() {
		return permission;
	}

	public void setPermission(boolean permission) {
		this.permission = permission;
	}

	public ArrayList<Offering> getSchedule() {
		return schedule;
	}

	public void setSchedule(ArrayList<Offering> schedule) {
		this.schedule = schedule;
	}

	public static int getMincredits() {
		return minCredits;
	}

	public static int getMaxcredits() {
		return maxCredits;
	}
}
