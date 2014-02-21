package implementation;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import entity.Course;
import entity.Offering;
import entity.Schedule;

public class ScheduleImpl{
	
	private static final int minCredits = 12;
	private static final int maxCredits = 18;
	private boolean permission = false;
	private Schedule schedule;
	
	public ScheduleImpl(Schedule schedule){
		this.schedule = schedule;
	}
	
	public void addOffering(Offering offering) {
		int credits = schedule.getCredits();
		credits += offering.getCourse().getCredits();
		schedule.setCredits(credits);
		schedule.getOfferings().add(offering);
	}

	public void authorizeOverload(boolean authorized) {
		permission = authorized;
	}

	public List<String> analysis() {
		ArrayList<String> result = new ArrayList<String>();
		if (schedule.getCredits() < minCredits)
			result.add("Too few credits");
		if (schedule.getCredits() > maxCredits && !permission)
			result.add("Too many credits");
		checkDuplicateCourses(result);
		checkOverlap(result);
		return result;
	}

	public void checkDuplicateCourses(ArrayList<String> analysis) {
		HashSet<Course> courses = new HashSet<Course>();
		for (int i = 0; i < schedule.getOfferings().size(); i++) {
			Course course = ((Offering) schedule.getOfferings().get(i)).getCourse();
			if (courses.contains(course))
				analysis.add("Same course twice - " + course.getName());
			courses.add(course);
		}
	}

	public void checkOverlap(ArrayList<String> analysis) {
		HashSet<String> times = new HashSet<String>();
		for (Iterator<Offering> iterator = schedule.getOfferings().iterator(); iterator.hasNext();) {
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
	
	public boolean isPermission() {
		return permission;
	}

	public void setPermission(boolean permission) {
		this.permission = permission;
	}
	
	public static int getMincredits() {
		return minCredits;
	}

	public static int getMaxcredits() {
		return maxCredits;
	}

	public String toString() {
		return "Schedule " + schedule.getName() + ": " + schedule;
	}

}
