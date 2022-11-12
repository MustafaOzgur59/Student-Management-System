package iteration1;

import java.io.IOException;

public class System {
    private StudentManager studentManager = new StudentManager();

    private JsonParser parser = new JsonParser();

    private Curriculum curriculum=new Curriculum();

    private StudentGenerator studentGenerator = new StudentGenerator();


    public System() {

    }

    public StudentManager getStudentManager() {
        return studentManager;
    }

    public void setStudentManager(StudentManager studentManager) {
        this.studentManager = studentManager;
    }

    public JsonParser getParser() {
        return parser;
    }

    public void setParser(JsonParser parser) {
        this.parser = parser;
    }

    public Curriculum getCurriculum() {
        return curriculum;
    }

    public void loadStudentAndCourses() throws IOException {
        this.parser.parseCourseObjects(this.curriculum);
        this.parser.parseStudents();
        //this.studentGenerator.generateStudents();
    }

    public void setCurriculum(Curriculum curriculum) {
        this.curriculum = curriculum;
    }
}
