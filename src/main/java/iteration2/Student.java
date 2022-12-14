package iteration2;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;


public class Student extends Person {
    private Integer term;
    private ArrayList<String> enrolledCourses=new ArrayList<>();
    private StudentSemester studentSemester;
    private Transcript transcript = new Transcript();
    private Advisor advisor;
    private ArrayList<String> logs=new ArrayList<>();

    public Student(String id, String name, Integer term) {
        super(id,name);
        this.term = term;
        studentSemester = new StudentSemester(term);
    }

    @JsonCreator
    public Student(
            @JsonProperty("id") String id,
            @JsonProperty("name") String name,
            @JsonProperty("term") Integer term,
            @JsonProperty("transcript") Transcript transcript) {
        super(id,name);
        this.term = term;
        this.transcript=transcript;
        this.enrolledCourses = new ArrayList<>();
        this.studentSemester = new StudentSemester(this.term);
    }

    public Student() {
    }

    public Integer getTerm() {
        return term;
    }

    public void setTerm(Integer term) {
        this.term = term;
    }

    public ArrayList<String> getEnrolledCourses() {
        return enrolledCourses;
    }

    public void setEnrolledCourses(ArrayList<String> enrolledCourses) {
        this.enrolledCourses = enrolledCourses;
    }

    public StudentSemester getStudentSemester() {
        return studentSemester;
    }

    public void setStudentSemester(StudentSemester studentSemester) {
        this.studentSemester = studentSemester;
    }

    public Transcript getTranscript() {
        return transcript;
    }

    public void setTranscript(Transcript transcript) {
        this.transcript = transcript;
    }

    public Advisor getAdvisor() {
        return advisor;
    }

    public void setAdvisor(Advisor advisor) {
        this.advisor = advisor;
    }

    public ArrayList<String> getLogs() {
        return logs;
    }

    public void setLogs(ArrayList<String> logs) {
        this.logs = logs;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return Objects.equals(term, student.term) && Objects.equals(enrolledCourses, student.enrolledCourses) && Objects.equals(studentSemester, student.studentSemester) && Objects.equals(transcript, student.transcript) && Objects.equals(advisor, student.advisor) && Objects.equals(logs, student.logs);
    }

    @Override
    public int hashCode() {
        return Objects.hash(term, enrolledCourses, studentSemester, transcript, advisor, logs);
    }

    @Override
    public String toString() {
        return "Student{" +
                "term=" + term +
                ", enrolledCourses=" + enrolledCourses +
                ", studentSemester=" + studentSemester +
                ", transcript=" + transcript +
                ", advisor=" + advisor +
                ", logs=" + logs +
                "} " + super.toString();
    }

    public void enroll(ArrayList<Course> availableCourses, Curriculum curriculum, SystemParameter systemParameters){
        for (Course availableCourse : availableCourses) {
            if (availableCourse.getCode().equals("TExxx")){
                Random random = new Random();
                Course course = curriculum.getTE_COURSES().get(random.nextInt(curriculum.getTE_COURSES().size()));
                advisor.enrollStudent(course, this, curriculum,systemParameters);
            }
            else{
                advisor.enrollStudent(availableCourse, this, curriculum,systemParameters);
            }
        }
    }


}
