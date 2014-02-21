package entity;

public class Offering {
	private int id;
	private Course course;
	private String daysTimes;
	
	//Constructor
	public Offering(int id, Course course, String daysTimesCsv) {
		this.id = id;
		this.course = course;
		this.daysTimes = daysTimesCsv;
	}
	
	//Empty Constructor
	public Offering(){
		
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
