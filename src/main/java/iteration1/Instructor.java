package iteration1;
import java.util.Random;
import java.util.ArrayList;
..
public class Instructor extends FacultyMember {

	
	private ArrayList<Course> coursesOfferedList;

	public Instructor(String name, String id) {
		super(name, id);
	}
	
	public void gradeStudents(Student student, Course course) {
		
		Random rand = new Random();
		float int_random = (float)rand.nextInt(9)/2;
		student.getCurrentSemester().getCourses().add(course.getCourseCode(),int_random);	
	}

	public ArrayList<Course> getCoursesOfferedList() {
		return coursesOfferedList;
	}

	public void setCoursesOfferedList(ArrayList<Course> coursesOfferedList) {
		this.coursesOfferedList = coursesOfferedList;
	}

	
	
}
