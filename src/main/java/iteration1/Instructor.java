package iteration1;
import java.util.Random;
import java.util.ArrayList;

public class Instructor extends FacultyMember {


	private ArrayList<Course> coursesOfferedList;

	public Instructor(String name, String id) {
		super(name, id);
		coursesOfferedList = new ArrayList<>();
	}

	public void gradeStudents(Student student, Course course) {
		Random rand = new Random();
		float int_random = (float)rand.nextInt(9)/2;
		student.getStudentSemester().getGivenCourses().add(new GivenCourse(course.getCode(),int_random,course.getCredit()));
	}

	public ArrayList<Course> getCoursesOfferedList() {
		return coursesOfferedList;
	}

	public void setCoursesOfferedList(ArrayList<Course> coursesOfferedList) {
		this.coursesOfferedList = coursesOfferedList;
	}



}
