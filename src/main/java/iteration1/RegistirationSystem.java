package iteration1;

import java.io.IOException;
import java.util.ArrayList;


public class System {
    private StudentManager studentManager = new StudentManager();

    private JsonParser parser = new JsonParser();

    private Curriculum curriculum=new Curriculum();

    private Instructor instructor = new Instructor("name","name");


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

        //this.studentGenerator.generateStudents();
    }

    public void beginSimulation() throws IOException {
        for (Student student : studentManager.getStudentList()){
            ArrayList<Course> availableCourses = getAvailableCourses(student);

            student.enroll(availableCourses,curriculum);
        }

        for (Course course : studentManager.getCoursesList()){
            course.ge
        }


        this.parser.outputStudentObjectsWithProblems(this.studentManager.getStudentList());
    }


    /*
     * TODO
     *  return available courses for particular student
     */
    public ArrayList<Course> getAvailableCourses(Student student) {
        ArrayList<Course>[] courses = getCurriculum().getCOURSES();
        ArrayList<Course> availableCourses = new ArrayList<>();
        boolean isFound = false;
        for(int i=0; i<student.getTranscript().getSemesters().size(); i++) {
            ArrayList<GivenCourse> givenCourses=student.getTranscript().getSemesters().get(i).getGivenCourses();
            for (int j=0; j<courses[i].size();j++){
                for (int k=0;k<givenCourses.size();k++){
                    if(givenCourses.get(k).getCourseCode().equals(courses[i].get(j).getCode())){
                        isFound = true;
                        if(givenCourses.get(k).getGrade() < 2)
                            availableCourses.add(courses[i].get(j));
                    }
                }
                if (!isFound)
                    availableCourses.add(courses[i].get(j));
                isFound=false;
            }
        }
        for (int i=student.getTranscript().getSemesters().size();i<8;i++){
            for (ArrayList<Course> coursesRow: courses){
                for (Course course: coursesRow){
                    availableCourses.add(course);
                }
            }
        }
        return availableCourses;
    }

    public void setCurriculum(Curriculum curriculum) {
        this.curriculum = curriculum;
    }
}
