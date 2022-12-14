package iteration2;


import com.fasterxml.jackson.annotation.JsonProperty;
import iteration2.Course;
import iteration2.Student;

public abstract class FacultyMember extends Person {

	public FacultyMember(String name, String id) {
		super(name,id);
	}

	public abstract void gradeStudents(Student student, Course course);

	@Override
	public String toString() {
		return "FacultyMember{} " + super.toString();
	}


}
