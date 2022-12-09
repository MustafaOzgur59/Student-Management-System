package iteration2;



import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;


public class RegistrationSystem {
    private StudentManager studentManager = new StudentManager();

    private JsonParser parser = new JsonParser();

    private Curriculum curriculum=new Curriculum();

    private Instructor instructor = new Instructor("dummy","dummy");

    private SystemParameter systemParameter;

    private static final Logger logger = LogManager.getLogger(RegistrationSystem.class);

    public RegistrationSystem() {

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
        this.parser.parseCourseObjects(this.curriculum,this.instructor);
        this.parser.parseAdvisors(this.studentManager);
    }

    public void beginSimulation() throws IOException {
        this.initializeStudents();
        this.prepareInitializedStudents();
        logger.info("Starting simulation");
        this.parser.outputStudentObjectsWithProblems(
                this.studentManager.getStudentList(),
                "./src/main/java/students/inputStudents/");
        enrollStudents();
        gradeStudents();
        calculateTranscript();
        this.parser.outputStudentObjectsWithProblems(
                this.studentManager.getStudentList(),
                "./src/main/java/students/outputStudents/");
        logger.info("Ending simulation");
    }


    public void enrollStudents(){
        // courseleri enroll eder
        for (Student student : studentManager.getStudentList()){
            ArrayList<Course> availableCourses = getAvailableCourses(student);
            student.enroll(availableCourses,curriculum,systemParameter);
        }

    }

    public void gradeStudents(){
        // course alan bütün öğrencileri gradeler
        for (Course course : this.instructor.getCoursesOfferedList()){
            for (String studentId : course.getEnrolledStudents()){
                this.instructor.gradeStudents(studentManager.getStudent(studentId),course);
            }
        }
    }

    public void calculateTranscript(){
        // dönem sonu semesterlerin yanosunu hesapla sonra transciprt gpa hesapla ve ekle
        for (Student student : studentManager.getStudentList()){
            student.getTranscript().getSemesters().add(student.getStudentSemester());
            for (StudentSemester semester : student.getTranscript().getSemesters()){
                semester.calculateYano();
                semester.setLetterGrades(new ArrayList<>());
                semester.calculateLetterGrade();
            }
            student.getTranscript().calculateGpa();
        }
    }

    /*
     * TODO
     *  return available courses for particular student
     */
    // potential bug here,somehow returns passed course of  the parameter student
    public ArrayList<Course> getAvailableCourses(Student student) {
        ArrayList<Course>[] courses = getCurriculum().getCOURSES();
        ArrayList<Course> availableCourses = new ArrayList<>();
        // if studentSemesters size is 0 directly goes into this loop and adds all courses
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
            int term = 2 * (i-1) + 1;
            String departmentCode= "1501";
            String entryYear = Integer.toString(22 - i);
            for (int j=1;j<=this.systemParameter.getStudentPerSemester();j++){
                String entryPlace = j < 10 ? "00"+j : "0"+j ;
                String studentNumber = departmentCode + entryYear + entryPlace;
                Student student = new Student(studentNumber,studentNumber, term);
                Advisor advisor = studentManager.getAdvisorList().get(new Random().nextInt(studentManager.getAdvisorList().size()));
                student.setAdvisor(advisor);
                logger.info("Added advisor" + advisor.getName() + " to student " + student.getName() );
                studentManager.getStudentList().add(student);
            }
        }
    }

    public void prepareInitializedStudents() throws IOException {
        for (int i=1;i<=4;i++){
            int currentStudentTerm = 2 * i -1;
            for (int k=1;k<currentStudentTerm;k++){
                for (int j=0;j<this.systemParameter.getStudentPerSemester();j++){
                    Student student =this.studentManager.getStudentList()
                            .get( (i-1) * this.systemParameter.getStudentPerSemester() + j );
                    student.setTerm(k);
                    student.setStudentSemester(new StudentSemester(k));
                    student.setEnrolledCourses(new ArrayList<String>());
                    ArrayList<Course> availableCourses = getAvailableCourses(student);
                    student.enroll(availableCourses,curriculum,systemParameter);
                    //grade student
                    for (String courseName: student.getEnrolledCourses()){

                        this.instructor.gradeStudents(student, curriculum.getCourse(courseName));
                    }
                    student.getStudentSemester().calculateLetterGrade();
                    student.getStudentSemester().calculateYano();
                    student.getTranscript().getSemesters().add(student.getStudentSemester());
                    student.getTranscript().calculateGpa();
                    student.setTerm(currentStudentTerm);
                }
                this.curriculum = new Curriculum();
                this.parser.parseCourseObjects(this.curriculum,this.instructor);
            }
        }
        for (Student student : this.studentManager.getStudentList()){
            student.setStudentSemester(new StudentSemester(student.getTerm()));
            student.setEnrolledCourses(new ArrayList<>());
            student.setLogs(new ArrayList<>());
            System.out.println("Student is : " + student.toString());
        }
        this.curriculum = new Curriculum();
        // TODO
        // CREATE NEW INSTRUCTORS
        this.instructor = new Instructor("dummy","12414");
        this.parser.parseCourseObjects(this.curriculum,this.instructor);
    }

    public void setCurriculum(Curriculum curriculum) {
        this.curriculum = curriculum;
    }
}
