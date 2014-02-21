package studentTimetable;

import implementation.ScheduleImpl;
import junit.framework.TestCase;
import dao.CourseDao;
import dao.OfferingDao;
import dao.ScheduleDao;
import entity.Course;
import entity.Offering;
import entity.Schedule;

public class TestReport extends TestCase {

	public TestReport(String name) { 
		super(name); 
	}
	
	public void testEmptyReport() throws Exception {
		ScheduleDao.deleteAll();
		Report report = new Report();
		StringBuffer buffer = new StringBuffer();
		report.write(buffer);
		assertEquals("Number of scheduled offerings: 0\n", buffer.toString());
	}
	
	//TODO Edited
	public void testReport() throws Exception {
		ScheduleDao.deleteAll();
		Course cs101 = CourseDao.create("CS101", 3);
		CourseDao.update(cs101);
		Offering off1 = OfferingDao.create(cs101, "M10");
		OfferingDao.update(off1, cs101);
		Offering off2 = OfferingDao.create(cs101, "T9");
		OfferingDao.update(off2, cs101);
		Schedule s = ScheduleDao.create("Bob");
		ScheduleImpl scheduleImpl1 = new ScheduleImpl(s);
		scheduleImpl1.addOffering(off1);
		scheduleImpl1.addOffering(off2);
		ScheduleDao.update(s);
		Schedule s2 = ScheduleDao.create("Alice");
		ScheduleImpl scheduleImpl2 = new ScheduleImpl(s2);
		scheduleImpl2.addOffering(off1);
		ScheduleDao.update(s2);
		Report report = new Report();
		StringBuffer buffer = new StringBuffer();
		report.write(buffer);
		String result = buffer.toString();
		String valid1 = "CS101 M10\n\tAlice\n\tBob\n" + "CS101 T9\n\tBob\n" + "Number of scheduled offerings: 2\n";
		String valid2 = "CS101 T9\n\tBob\n" + "CS101 M10\n\tBob\n\tAlice\n" + "Number of scheduled offerings: 2\n";
		assertTrue(result.equals(valid1) || result.equals(valid2));
	}
}
