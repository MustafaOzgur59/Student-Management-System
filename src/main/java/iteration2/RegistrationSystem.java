package iteration2;



import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;


public class RegistrationSystem {
    private Department department = new Department();

    private JsonParser parser = new JsonParser();

    private Curriculum curriculum=new Curriculum();

    private Instructor instructor = new Instructor("dummy","dummy");

    private SystemParameter systemParameter;

    private static final Logger logger = LogManager.getLogger(RegistrationSystem.class);

    public RegistrationSystem() {

    }

    public Department getStudentManager() {
        return department;
    }

    public void setStudentManager(Department department) {
        this.department = department;
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

    public Instructor getInstructor() {
        return instructor;
    }

    public void setInstructor(Instructor instructor) {
        this.instructor = instructor;
    }

    public SystemParameter getSystemParameter() {
        return systemParameter;
    }

    public void setSystemParameter(SystemParameter systemParameter) {
        this.systemParameter = systemParameter;
    }

    public void readJsonFiles() throws IOException {
        this.systemParameter=this.parser.parseParameters();
        this.parser.parseAdvisors(this.department);
        Instructor [] instructors = this.department.getAdvisorList().toArray(new Instructor[0]);
        this.parser.parseCourseObjects(this.curriculum, instructors );
    }

    public void beginSimulation() throws IOException {
        this.initializeStudents();
        this.prepareInitializedStudents();
        logger.info("Starting simulation");
        this.parser.outputStudentObjects(
                this.department.getStudentList(),
                "./src/main/java/students/inputStudents/");
        enrollStudents();
        gradeStudents();
        calculateTranscript();
        this.parser.outputStudentObjects(
                this.department.getStudentList(),
                "./src/main/java/students/outputStudents/");
        logger.info("Ending simulation");
    }


    public void enrollStudents(){

        for (Student student : department.getStudentList()){
            ArrayList<Course> availableCourses = getAvailableCourses(student);
            student.enroll(availableCourses,curriculum,systemParameter);
        }
    }

    public void gradeStudents(){
        for (Instructor advisor : this.department.getAdvisorList().toArray(new Instructor[0])){
            for (Course course : advisor.getCoursesOfferedList()){
                for (String studentId : course.getEnrolledStudents()){
                    advisor.gradeStudents(department.getStudent(studentId),course);

                }
            }
        }
    }

    public void calculateTranscript(){
        for (Student student : department.getStudentList()){
            student.getTranscript().getSemesters().add(student.getStudentSemester());
            for (StudentSemester semester : student.getTranscript().getSemesters()){
                semester.calculateYano();
                semester.setLetterGrades(new ArrayList<>());
                semester.calculateLetterGrade();
            }
            student.getTranscript().calculateGpa();
        }
    }

    public ArrayList<Course> getAvailableCourses(Student student) {
        ArrayList<Course>[] courses = getCurriculum().getCOURSES();
        ArrayList<Course> availableCourses = new ArrayList<>();
        for (ArrayList<Course> courseList : courses){
            for (Course course : courseList){
                if (student.getTranscript().containsCourse(course.getCode())){
                    if(student.getTranscript().getMaxGrade(course.getCode()) < 2){
                        availableCourses.add(course);
                    }
                }
                else{
                    availableCourses.add(course);
                }
            }
        }
        return availableCourses;
    }

    public void initializeStudents(){
        for (int i=1;i<=4;i++){
            int term = 2 * (i-1) + this.systemParameter.getSemester();
            String departmentCode= "1501";
            String entryYear = Integer.toString(22 - i);
            for (int j=1;j<=this.systemParameter.getStudentPerSemester();j++){
                String entryPlace = j < 10 ? "00"+j : "0"+j ;
                String studentNumber = departmentCode + entryYear + entryPlace;
                Student student = new Student(studentNumber,studentNumber, term);
                Advisor advisor = department.getAdvisorList().get(new Random().nextInt(department.getAdvisorList().size()));
                student.setAdvisor(advisor);
                logger.info("Added advisor" + advisor.getName() + " to student " + student.getName() );
                department.getStudentList().add(student);
            }
        }
    }

    public void prepareInitializedStudents() throws IOException {
        for (int i=1;i<=4;i++){
            int currentStudentTerm = 2 * (i -1) + this.systemParameter.getSemester();
            for (int k=1;k<currentStudentTerm;k++){
                for (int j=0;j<this.systemParameter.getStudentPerSemester();j++){
                    Student student =this.department.getStudentList()
                            .get( (i-1) * this.systemParameter.getStudentPerSemester() + j );
                    student.setTerm(k);
                    student.setStudentSemester(new StudentSemester(k));
                    student.setEnrolledCourses(new ArrayList<String>());
                    ArrayList<Course> availableCourses = getAvailableCourses(student);
                    student.enroll(availableCourses,curriculum,systemParameter);
                    //grade student
                    this.gradeStudents();

                    student.getStudentSemester().calculateLetterGrade();
                    student.getStudentSemester().calculateYano();
                    student.getTranscript().getSemesters().add(student.getStudentSemester());
                    student.getTranscript().calculateGpa();
                    student.setTerm(currentStudentTerm);

                    // resetting curriculum
                    this.curriculum = new Curriculum();
                    Instructor[] instructors = this.parser.parseAdvisors(this.department).toArray(new Instructor[0]);
                    this.parser.parseCourseObjects(this.curriculum,instructors);
                }
                this.curriculum = new Curriculum();
                this.parser.parseCourseObjects(this.curriculum,this.department.getAdvisorList().toArray(new Advisor[0]));
            }
        }
        for (Student student : this.department.getStudentList()){
            student.setStudentSemester(new StudentSemester(student.getTerm()));
            student.setEnrolledCourses(new ArrayList<>());
            student.setLogs(new ArrayList<>());
            System.out.println("Student is : " + student.toString());
        }
        this.curriculum = new Curriculum();
        // TODO
        // CREATE NEW INSTRUCTORS
        Instructor[] instructors = this.parser.parseAdvisors(this.department).toArray(new Instructor[0]);
        this.parser.parseCourseObjects(this.curriculum,instructors);
    }

    public void setCurriculum(Curriculum curriculum) {
        this.curriculum = curriculum;
    }
}
