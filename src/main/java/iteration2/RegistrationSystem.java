package iteration2;

import iteration1.Course;
import iteration1.Curriculum;
import iteration1.GivenCourse;
import iteration1.Instructor;
import iteration1.JsonParser;
import iteration1.Student;
import iteration1.StudentManager;
import iteration1.StudentSemester;
import iteration1.SystemParameter;

import java.io.IOException;
import java.util.ArrayList;


public class RegistrationSystem {
    private iteration1.StudentManager studentManager = new iteration1.StudentManager();

    private iteration1.JsonParser parser = new iteration1.JsonParser();

    private iteration1.Curriculum curriculum=new iteration1.Curriculum();

    private iteration1.Instructor instructor = new iteration1.Instructor("dummy","dummy");

    private iteration1.SystemParameter systemParameter;


    public RegistrationSystem() {

    }

    public iteration1.StudentManager getStudentManager() {
        return studentManager;
    }

    public void setStudentManager(StudentManager studentManager) {
        this.studentManager = studentManager;
    }

    public iteration1.JsonParser getParser() {
        return parser;
    }

    public void setParser(JsonParser parser) {
        this.parser = parser;
    }

    public iteration1.Curriculum getCurriculum() {
        return curriculum;
    }

    public iteration1.Instructor getInstructor() {
        return instructor;
    }

    public void setInstructor(Instructor instructor) {
        this.instructor = instructor;
    }

    public iteration1.SystemParameter getSystemParameter() {
        return systemParameter;
    }

    public void setSystemParameter(SystemParameter systemParameter) {
        this.systemParameter = systemParameter;
    }

    public void readJsonFiles() throws IOException {
        this.systemParameter=this.parser.parseParameters();
        this.parser.parseCourseObjects(this.curriculum,this.instructor);
        this.parser.parseStudents(this.studentManager);
        /*
         * TODO
         *  -replace outputStudentObjects function to the end of the simulation
         * */

        //this.studentGenerator.generateStudents();
    }

    public void beginSimulation() throws IOException {
        // courseleri enroll eder
        for (iteration1.Student student : studentManager.getStudentList()){
            ArrayList<iteration1.Course> availableCourses = getAvailableCourses(student);
            student.enroll(availableCourses,curriculum);
        }

        // course alan bütün öğrencileri gradeler
        for (iteration1.Course course : this.instructor.getCoursesOfferedList()){
            for (String studentId : course.getEnrolledStudents()){
                this.instructor.gradeStudents(studentManager.getStudent(studentId),course);
            }
        }

        // dönem sonu semesterlerin yanosunu hesapla sonra transciprt gpa hesapla ve ekle
        for (iteration1.Student student : studentManager.getStudentList()){
            student.getTranscript().getSemesters().add(student.getStudentSemester());
            for (StudentSemester semester : student.getTranscript().getSemesters()){
                semester.calculateYano();
                semester.calculateLetterGrade();
            }
            student.getTranscript().calculateGpa();
        }


        this.parser.outputStudentObjectsWithProblems(this.studentManager.getStudentList());
    }


    /*
     * TODO
     *  return available courses for particular student
     */
    public ArrayList<iteration1.Course> getAvailableCourses(Student student) {
        // 0-> 8  1->8 2->6 3->5
        ArrayList<iteration1.Course>[] courses = getCurriculum().getCOURSES();
        ArrayList<iteration1.Course> availableCourses = new ArrayList<>();
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
            for (Course course: courses[i]){
                 availableCourses.add(course);
            }
        }
        return availableCourses;
    }

    public void setCurriculum(Curriculum curriculum) {
        this.curriculum = curriculum;
    }
}
