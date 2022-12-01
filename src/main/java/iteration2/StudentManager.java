package iteration2;

import iteration2.Advisor;
import iteration2.Course;
import iteration2.Student;

import java.util.ArrayList;
import java.util.List;

public class StudentManager {
    private List<iteration1.Student> studentList;
    private List<iteration1.Course> coursesList;
    private Advisor advisor;

    public StudentManager() {
        this.studentList = new ArrayList<>();
        this.coursesList = new ArrayList<>();
    }

    public boolean addStudent(iteration1.Student student, iteration1.Course course) {
        if(course.getQuota() > course.getEnrolledStudents().size()) {
            course.getEnrolledStudents().add(student.getId());
            return true;
        }
        else{
            return false;
        }
    }
    public boolean searchStudent(iteration1.Student student, iteration1.Course course) {
        return course.getEnrolledStudents().contains(student);
    }
    public void removeStudent(iteration1.Student student, iteration1.Course course) {
        course.getEnrolledStudents().remove(student);
    }
    public List<iteration1.Student> getStudentList() {
        return studentList;
    }
    public void setStudentList(List<iteration1.Student> studentList) {
        this.studentList = studentList;
    }

    public List<iteration1.Course> getCoursesList() {
        return coursesList;
    }

    public void setCoursesList(List<Course> coursesList) {
        this.coursesList = coursesList;
    }

    public iteration1.Student getStudent(String studentId){
        for (Student student : this.studentList){
            if (student.getId().equals(studentId)) return student;
        }
        return null;
    }
}
