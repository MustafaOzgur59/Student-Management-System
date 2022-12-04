package iteration2;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.ArrayList;
import java.util.Objects;

@JsonIgnoreProperties(value={"enrolledCourses","studentSemester","advisor"},allowGetters = true)
@JsonPropertyOrder({"id","name","term","transcript",""})
public class Student {
    @JsonProperty("id")
    private String id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("term")
    private Integer term;

    private ArrayList<String> enrolledCourses=new ArrayList<>();

    private StudentSemester studentSemester;
    @JsonProperty("transcript")
    private Transcript transcript = new Transcript();

    private Advisor advisor = new Advisor("dummy","124");

    @JsonProperty("logs")
    private ArrayList<String> logs=new ArrayList<>();

    public Student(String id, String name, Integer term) {
        this.id = id;
        this.name = name;
        this.term = term;
        studentSemester = new StudentSemester(term);
    }

    @JsonCreator
    public Student(
            @JsonProperty("id") String id,
            @JsonProperty("name") String name,
            @JsonProperty("term") Integer term,
            @JsonProperty("transcript") Transcript transcript) {
        this.id = id;
        this.name = name;
        this.term = term;
        this.transcript=transcript;
        this.enrolledCourses = new ArrayList<>();
        this.studentSemester = new StudentSemester(this.term);
    }

    public Student() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
        return Objects.equals(id, student.id) && Objects.equals(name, student.name) && Objects.equals(term, student.term);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, term);
    }

    public void enroll(ArrayList<Course> availableCourses, Curriculum curriculum, SystemParameter systemParameters){
        for (Course availableCourse : availableCourses) {
            advisor.enrollStudent(availableCourse, this, curriculum,systemParameters);
        }
    }

    @Override
    public String toString() {
        return "Student{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", term=" + term +
                ", enrolledCourses=" + enrolledCourses +
                ", studentSemester=" + studentSemester +
                ", transcript=" + transcript +
                ", advisor=" + advisor +
                ", logs=" + logs +
                '}';
    }
}
