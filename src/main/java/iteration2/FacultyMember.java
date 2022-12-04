package iteration2;


import iteration2.Course;
import iteration2.Student;

public abstract class FacultyMember {
	
	

	private String name ;
	private String id ;

	public FacultyMember(String name, String id) {
		this.name = name;
		this.id = id;
		
	}

	public abstract void gradeStudents(Student student, Course course);
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
}
