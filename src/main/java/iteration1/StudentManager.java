package iteration1;

import java.util.ArrayList;
import java.util.List;

public class StudentManager {
    private List<Student> studentList ;
    private Advisor advisor;

    public StudentManager() {
        studentList = new ArrayList<>();
    }
    public void addStudent(Student student,Course course) {
        course.getEnrolledStudents().add(student);
    }
    public boolean searchStudent(Student student,Course course) {
        return course.getEnrolledStudents().contains(student);
    }
    public void removeStudent(Student student, Course course) {
        course.getEnrolledStudents().remove(student);
    }
    public List<Student> getStudentList() {
        return studentList;
    }
    public void setStudentList(List<Student> studentList) {
        this.studentList = studentList;
    }
}
