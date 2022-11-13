package iteration1;

import java.util.ArrayList;
import java.util.List;

public class StudentManager {
    private List<Student> studentList;
    private List<Course> coursesList;
    private Advisor advisor;

    public StudentManager() {
        this.studentList = new ArrayList<>();
        this.coursesList = new ArrayList<>();
    }

    public boolean addStudent(Student student, Course course) {
        if(course.getQuota() > course.getEnrolledStudents().size()) {
            course.getEnrolledStudents().add(student.getId());
            return true;
        }
        else{
            return false;
        }
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

    public List<Course> getCoursesList() {
        return coursesList;
    }

    public void setCoursesList(List<Course> coursesList) {
        this.coursesList = coursesList;
    }

    public Student getStudent(String studentId){
        for (Student student : this.studentList){
            if (student.getId().equals(studentId)) return student;
        }
        return null;
    }
}
