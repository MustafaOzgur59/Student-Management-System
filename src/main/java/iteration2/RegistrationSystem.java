package iteration2;

import iteration2.Course;
import iteration2.Curriculum;
import iteration2.GivenCourse;
import iteration2.Instructor;
import iteration2.JsonParser;
import iteration2.Student;
import iteration2.StudentManager;
import iteration2.StudentSemester;
import iteration2.SystemParameter;

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
        //this.parser.parseStudents(this.studentManager);
        this.initializeStudents();
        this.prepareInitializedStudents();
        /*
         * TODO
         *  -replace outputStudentObjects function to the end of the simulation
         * */

        //this.studentGenerator.generateStudents();
    }

    public void beginSimulation() throws IOException {
        //enrollStudents();
        //gradeStudents();
        //calculateTranscript();
        this.parser.outputStudentObjectsWithProblems(this.studentManager.getStudentList());
    }

    public void enrollStudents(){
        // courseleri enroll eder
        for (iteration1.Student student : studentManager.getStudentList()){
            ArrayList<iteration1.Course> availableCourses = getAvailableCourses(student);
            student.enroll(availableCourses,curriculum,systemParameter);
        }
    }

    public void gradeStudents(){
        // course alan bütün öğrencileri gradeler
        for (iteration1.Course course : this.instructor.getCoursesOfferedList()){
            for (String studentId : course.getEnrolledStudents()){
                this.instructor.gradeStudents(studentManager.getStudent(studentId),course);
            }
        }
    }

    public void calculateTranscript(){
        // dönem sonu semesterlerin yanosunu hesapla sonra transciprt gpa hesapla ve ekle
        for (iteration1.Student student : studentManager.getStudentList()){
            student.getTranscript().getSemesters().add(student.getStudentSemester());
            for (iteration1.StudentSemester semester : student.getTranscript().getSemesters()){
                semester.calculateYano();
                semester.calculateLetterGrade();
            }
            student.getTranscript().calculateGpa();
        }
    }

    /*
     * TODO
     *  return available courses for particular student
     */
    public ArrayList<iteration1.Course> getAvailableCourses(iteration1.Student student) {
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
            for (iteration1.Course course: courses[i]){
                 availableCourses.add(course);
            }
        }
        return availableCourses;
    }

    public void initializeStudents(){
        for (int i=1;i<=2;i++){
            int term = 2 * (i-1) + 1;
            String departmentCode= "1501";
            String entryYear = Integer.toString(22 - i);
            for (int j=1;j<=this.systemParameter.getStudentPerSemester();j++){
                String entryPlace = j < 10 ? "00"+j : "0"+j ;
                String studentNumber = departmentCode + entryYear + entryPlace;
                studentManager.getStudentList().add(new iteration1.Student(studentNumber,studentNumber, term));
            }
        }
    }

    public void prepareInitializedStudents() throws IOException {
        for (int i=1;i<=2;i++){
            int maxTerm = 2 * i -1;
            for (int k=1;k<maxTerm;k++){
                for (int j=0;j<this.systemParameter.getStudentPerSemester();j++){
                    Student student =this.studentManager.getStudentList()
                            .get( (i-1) * this.systemParameter.getStudentPerSemester() + j );
                    int termOfStudent = student.getTerm();
                    student.setTerm(k);
                    student.setEnrolledCourses(new ArrayList<String>());
                    ArrayList<iteration1.Course> availableCourses = getAvailableCourses(student);
                    for (Course course : availableCourses){
                        System.out.println("Available course : " + course.getName());
                    }
                    student.setStudentSemester(new iteration1.StudentSemester(k));
                    student.enroll(availableCourses,curriculum,systemParameter);
                    //grade student
                    for (String courseName: student.getEnrolledCourses()){
                        //System.out.println("Course code is : " + courseName);
                        this.instructor.gradeStudents(student, curriculum.getCourse(courseName));
                    }
                    student.getTranscript().getSemesters().add(student.getStudentSemester());
                    for (StudentSemester semester : student.getTranscript().getSemesters()){
                        semester.calculateYano();
                        semester.calculateLetterGrade();
                    }
                    student.getTranscript().calculateGpa();
                    student.setTerm(termOfStudent);
                }
                this.parser.parseCourseObjects(this.curriculum,this.instructor);
            }
        }

    }

    public void setCurriculum(Curriculum curriculum) {
        this.curriculum = curriculum;
    }
}
