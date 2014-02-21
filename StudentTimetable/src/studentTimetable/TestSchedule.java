package studentTimetable;

import implementation.ScheduleImpl;
import junit.framework.TestCase;

import java.util.List;
import java.util.Collection;

import dao.CourseDao;
import dao.OfferingDao;
import dao.ScheduleDao;
import entity.Course;
import entity.Offering;
import entity.Schedule;

public class TestSchedule extends TestCase {
	
	public TestSchedule(String name) {
		super(name);
	}
	
	public void testMinCredits() {
		Schedule schedule = new Schedule("name");
		ScheduleImpl scheduleImpl = new ScheduleImpl(schedule);
		Collection<String> analysis = scheduleImpl.analysis();
		assertEquals(1, analysis.size());
		assertTrue(analysis.contains("Too few credits"));
	}
	
	public void testJustEnoughCredits() {
		Course cs110 = new Course("CS110", 11);
		Offering mwf10 = new Offering(1, cs110, "M10,W10,F10");
		Schedule schedule = new Schedule("name");
		ScheduleImpl scheduleImpl = new ScheduleImpl(schedule);
		scheduleImpl.addOffering(mwf10);
		List<String> analysis = scheduleImpl.analysis();
		assertEquals(1, analysis.size());
		assertTrue(analysis.contains("Too few credits"));
		schedule = new Schedule("name");
		scheduleImpl = new ScheduleImpl(schedule);
		Course cs101 = new Course("CS101", 12);
		Offering th11 = new Offering(1, cs101, "T11,H11");
		scheduleImpl.addOffering(th11);
		analysis = scheduleImpl.analysis();
		assertEquals(0, analysis.size());
	}
	
	public void testMaxCredits() {
		Course cs110 = new Course("CS110", 20);
		Offering mwf10 = new Offering(1, cs110, "M10,W10,F10");
		Schedule schedule = new Schedule("name");
		ScheduleImpl scheduleImpl = new ScheduleImpl(schedule);
		scheduleImpl.addOffering(mwf10);
		List<String> analysis = scheduleImpl.analysis();
		assertEquals(1, analysis.size());
		assertTrue(analysis.contains("Too many credits"));
		scheduleImpl.authorizeOverload(true);
		analysis = scheduleImpl.analysis();
		assertEquals(0, analysis.size());
	}
	
	public void testJustBelowMax() {
		Course cs110 = new Course("CS110", 19);
		Offering mwf10 = new Offering(1, cs110, "M10,W10,F10");
		Schedule schedule = new Schedule("name");
		ScheduleImpl scheduleImpl = new ScheduleImpl(schedule);
		scheduleImpl.addOffering(mwf10);
		List<String> analysis = scheduleImpl.analysis();
		assertEquals(1, analysis.size());
		assertTrue(analysis.contains("Too many credits"));
		schedule = new Schedule("name");
		scheduleImpl = new ScheduleImpl(schedule);
		Course cs101 = new Course("CS101", 18);
		Offering th11 = new Offering(1, cs101, "T11,H11");
		scheduleImpl.addOffering(th11);
		analysis = scheduleImpl.analysis();
		assertEquals(0, analysis.size());
	}

	public void testDupCourses() {
		Course cs110 = new Course("CS110", 6);
		Offering mwf10 = new Offering(1, cs110, "M10,W10,F10");
		Offering th11 = new Offering(1, cs110, "T11,H11");
		Schedule schedule = new Schedule("name");
		ScheduleImpl scheduleImpl = new ScheduleImpl(schedule);
		scheduleImpl.addOffering(mwf10);
		scheduleImpl.addOffering(th11);
		List<String> analysis = scheduleImpl.analysis();
		assertEquals(1, analysis.size());
		assertTrue(analysis.contains("Same course twice - CS110"));
	}
	
	public void testOverlap() {
		Schedule schedule = new Schedule("name");
		ScheduleImpl scheduleImpl = new ScheduleImpl(schedule);
		Course cs110 = new Course("CS110", 6);
		Offering mwf10 = new Offering(1, cs110, "M10,W10,F10");
		scheduleImpl.addOffering(mwf10);
		Course cs101 = new Course("CS101", 6);
		Offering mixed = new Offering(1, cs101, "M10,W11,F11");
		scheduleImpl.addOffering(mixed);
		List<String> analysis = scheduleImpl.analysis();
		assertEquals(1, analysis.size());
		assertTrue(analysis.contains("Course overlap - M10"));
		Course cs102 = new Course("CS102", 1);
		Offering mixed2 = new Offering(1, cs102, "M9,W10,F11");
		scheduleImpl.addOffering(mixed2);
		analysis = scheduleImpl.analysis();
		assertEquals(3, analysis.size());
		assertTrue(analysis.contains("Course overlap - M10"));
		assertTrue(analysis.contains("Course overlap - W10"));
		assertTrue(analysis.contains("Course overlap - F11"));
	}
	
	public void testCourseCreate() throws Exception {
		Course c = (Course) CourseDao.create(new Course("CS202", 1));
		Course c2 = CourseDao.find("CS202");
		assertEquals("CS202", c2.getName());
		Course c3 = CourseDao.find("Nonexistent");
		assertNull(c3);
	}
	
	public void testOfferingCreate() throws Exception {
		Course c = (Course) CourseDao.create(new Course("CS202", 2));
		Offering offering = OfferingDao.create(c, "M10");
		assertNotNull(offering);
	}
	
	public void testPersistentSchedule() throws Exception {
		Schedule s = ScheduleDao.create("Bob");
		assertNotNull(s);
	}
	
	public void testScheduleUpdate() throws Exception {
		Course cs101 = (Course) CourseDao.create(new Course("CS101", 3));
		CourseDao.update(cs101);
		Offering off1 = OfferingDao.create(cs101, "M10");
		OfferingDao.update(off1, cs101);
		Offering off2 = OfferingDao.create(cs101, "T9");
		OfferingDao.update(off2, cs101);
		Schedule s = ScheduleDao.create("Bob");
		ScheduleImpl scheduleImpl = new ScheduleImpl(s);
		scheduleImpl.addOffering(off1);
		scheduleImpl.addOffering(off2);
		ScheduleDao.update(s);
		Schedule s2 = ScheduleDao.create("Alice");
		ScheduleImpl scheduleImpl2 = new ScheduleImpl(s2);
		scheduleImpl2.addOffering(off1);
		ScheduleDao.update(s2);
		Schedule s3 = ScheduleDao.find("Bob");
		assertEquals(2, s3.getOfferings().size());
		Schedule s4 = ScheduleDao.find("Alice");
		assertEquals(1, s4.getOfferings().size());
	}
}
