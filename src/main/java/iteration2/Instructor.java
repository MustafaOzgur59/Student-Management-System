package iteration2;
import iteration2.Course;
import iteration2.FacultyMember;
import iteration2.GivenCourse;
import iteration2.Student;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.Random;
import java.util.ArrayList;

public class Instructor extends FacultyMember {
	final Logger logger = LogManager.getLogger(Instructor.class);

	private ArrayList<iteration2.Course> coursesOfferedList;

	public Instructor(String name, String id) {
		super(name, id);
		coursesOfferedList = new ArrayList<>();
	}



	public void gradeStudents(Student student, Course course) {
		Random rand = new Random();
		float int_random = (float)rand.nextInt(9)/2;
		logger.info("Graded student "+ student.getName() + " for course : " + course.getName() + " grade is : " + int_random);
		student.getStudentSemester().getGivenCourses().add(new GivenCourse(course.getCode(),int_random,course.getCredit()));
	}

	public ArrayList<iteration2.Course> getCoursesOfferedList() {
		return coursesOfferedList;
	}

	public void setCoursesOfferedList(ArrayList<Course> coursesOfferedList) {
		this.coursesOfferedList = coursesOfferedList;
	}



}
