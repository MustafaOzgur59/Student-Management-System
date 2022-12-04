package iteration2;



import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;


public class RegistrationSystem {
    private StudentManager studentManager = new StudentManager();

    private JsonParser parser = new JsonParser();

    private Curriculum curriculum=new Curriculum();

    private Instructor instructor = new Instructor("dummy","dummy");

    private SystemParameter systemParameter;


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
        this.initializeStudents();
        this.prepareInitializedStudents();

        /*
         * TODO
         *  -replace outputStudentObjects function to the end of the simulation
         * */

        //this.studentGenerator.generateStudents();
    }

    public void beginSimulation() throws IOException {
        this.parser.outputStudentObjectsWithProblems(
                this.studentManager.getStudentList(),
                "./src/main/java/students/inputStudents/");
        enrollStudents();
        gradeStudents();
        calculateTranscript();
        this.parser.outputStudentObjectsWithProblems(
                this.studentManager.getStudentList(),
                "./src/main/java/students/outputStudents/");

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
                semester.calculateLetterGrade();
            }
            student.getTranscript().calculateGpa();
        }
    }

    /*
     * TODO
     *  return available courses for particular student
     */
    // potential bug here
    public ArrayList<Course> getAvailableCourses(Student student) {
        // 0-> 8  1->8 2->6 3->5
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
            for (Course course: courses[i]){
                 availableCourses.add(course);
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
                student.setAdvisor(studentManager.getAdvisorList().get(new Random().nextInt(studentManager.getAdvisorList().size())));
                studentManager.getStudentList().add(student);
            }
        }
    }
    // potential bug here
    public void prepareInitializedStudents() throws IOException {
        for (int i=1;i<=4;i++){
            int maxTerm = 2 * i -1;
            for (int k=1;k<maxTerm;k++){
                for (int j=0;j<this.systemParameter.getStudentPerSemester();j++){
                    Student student =this.studentManager.getStudentList()
                            .get( (i-1) * this.systemParameter.getStudentPerSemester() + j );
                    int termOfStudent = student.getTerm();
                    student.setTerm(k);
                    student.setEnrolledCourses(new ArrayList<String>());
                    ArrayList<Course> availableCourses = getAvailableCourses(student);
                    for (Course course : availableCourses){
                        System.out.println("Available course : " + course.getName());
                    }
                    student.setStudentSemester(new StudentSemester(k));
                    student.enroll(availableCourses,curriculum,systemParameter);
                    //grade student
                    for (String courseName: student.getEnrolledCourses()){

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
        for (Student student : this.studentManager.getStudentList()){
            student.setStudentSemester(new StudentSemester(student.getTerm()));
            student.setLogs(new ArrayList<>());
        }
    }

    public void setCurriculum(Curriculum curriculum) {
        this.curriculum = curriculum;
    }
}
