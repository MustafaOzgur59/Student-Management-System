import iteration2.*;
import org.junit.Before;
import org.junit.Test;
import java.io.IOException;
import java.util.ArrayList;
import static org.junit.Assert.*;

public class StudentTest {
    JsonParser parser = new JsonParser();
    Curriculum curriculum=new Curriculum();
    SystemParameter parameter;
    Instructor[] instructors = {new Instructor("David Guetta", "1233345")};
    Student student;
    Advisor advisor = new Advisor("advisor");
    Course course = new Course("course1","c1",1,1,6,100
            ,new ArrayList<String>()
            ,new ArrayList<Section>()
            ,new ArrayList<Section>());
    Course course2 = new Course("course2","c2",1,1,6,100
            ,new ArrayList<String>()
            ,new ArrayList<Section>()
            ,new ArrayList<Section>());
    Course courseToCollide = new Course("course3","c3",1,1,6,100
            ,new ArrayList<String>()
            ,new ArrayList<Section>()
            ,new ArrayList<Section>());

    @Before
    public void initialize() throws IOException {
        parser.parseCourseObjects(curriculum,instructors);
        parameter = parser.parseParameters();
        student = new Student("Sanji","1501210017",1,new Transcript());
        student.setAdvisor(advisor);
        course.getCourseSessions().add(new Section("Wednesday","13:00-13:50"));
        course2.getPrerequisiteTo().add("c1");
        courseToCollide.getCourseSessions().add(new Section("Wednesday","13:30-15:20"));
    }


    @Test
    public void testStudentEnroll(){
        ArrayList<Course> courses = new ArrayList<>();
        courses.add(course);
        student.enroll(courses,curriculum,parameter);
        assertNotNull("Enrolled course array is null",student.getEnrolledCourses());
        assertEquals("course1", student.getEnrolledCourses().get(0));
    }

    @Test
    public void testStudentGrade(){
        ArrayList<Course> courses = new ArrayList<>();
        courses.add(course);
        student.enroll(courses,curriculum,parameter);
        instructors[0].gradeStudents(student,course);
        student.getTranscript().getSemesters().add(student.getStudentSemester());
        assertNotEquals(-1,student.getTranscript().getSemesters().get(0).getGivenCourses().get(0).getGrade());
        assertNotEquals(5,student.getTranscript().getSemesters().get(0).getGivenCourses().get(0).getGrade());
        assertNotNull(student.getTranscript().getSemesters().get(0).getGivenCourses().get(0));
    }

    @Test
    public void testCourseWithPrerquisite(){
            ArrayList<Course> courses = new ArrayList<>();
            courses.add(course);
            courses.add(course2);
            student.enroll(courses,curriculum,parameter);
            String[] enrolledCourses =  (String[]) student.getEnrolledCourses().toArray(new String[0]);
            String[] expectedCourses = {"course1"};
            assertArrayEquals(expectedCourses,enrolledCourses);
    }
}
