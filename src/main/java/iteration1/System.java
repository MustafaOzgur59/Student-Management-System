package iteration1;

import java.io.IOException;
import java.util.ArrayList;


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
        this.parser.parseStudents(this.studentManager);
        /*
        * TODO
        *  -replace outputStudentObjects function to the end of the simulation
        * */
        this.parser.outputStudentObjects(this.studentManager.getStudentList());
        //this.studentGenerator.generateStudents();
    }

    public void beginSimulation(){

        for (Student student : studentManager.getStudentList()){
            ArrayList<Course> availableCourses = getAvailableCourses(student);
            student.enroll(availableCourses);
        }
    }


    /*
     * TODO
     *  return available courses for particular student
     */
    public ArrayList<Course> getAvailableCourses(Student student) {
        return  null;
    }

    public void setCurriculum(Curriculum curriculum) {
        this.curriculum = curriculum;
    }
}
