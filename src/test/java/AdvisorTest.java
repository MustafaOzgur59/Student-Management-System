import iteration2.*;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;

public class AdvisorTest {
    private Advisor[] advisors = {new Advisor("advisor")};
    private Student student;
    private Curriculum curriculum = new Curriculum();
    private JsonParser parser = new JsonParser();
    private SystemParameter parameters = new SystemParameter(1,70,10,10,40);


    @Before
    public void initialize() throws IOException {
        student = new Student("Sanji","1501210017",1,new Transcript());
        student.setAdvisor(advisors[0]);
        parser.parseCourseObjects(curriculum,advisors);
        parameters.setMaxCoursePerSemester(10);
    }

    @Test
    public void testCourseEnrollWhenNoConflict(){
        ArrayList<Course> courses = new ArrayList<>();
        courses.add(curriculum.getCourseByCode("CSE1241"));
        student.enroll(courses,curriculum,parameters);
        assertEquals(courses.get(0).getName(), student.getEnrolledCourses().get(0));
    }

    @Test
    public void testCourseEnrollWhenTwoUpperSemester(){
        ArrayList<Course> courses = new ArrayList<>();
        courses.add(curriculum.getCOURSES()[2].get(0));
        student.enroll(courses,curriculum,parameters);
        assertEquals(0,student.getEnrolledCourses().size());
    }

    @Test
    public void testCourseEnrollWhenCollidingCourses(){
        Course course1 = curriculum.getCourseByCode("CSE1241");
        Course course2 = curriculum.getCourseByCode("MATH1001");
        ArrayList<Course> courses = new ArrayList<>();
        courses.add(course1);
        courses.add(course2);
        student.enroll(courses,curriculum,parameters);
        assertEquals(1, student.getEnrolledCourses().size());
        assertEquals(student.getEnrolledCourses().get(0), course1.getName());
    }

    @Test
    public void testCourseEnrollWhenMaxCourseLimitReached(){
        parameters.setMaxCoursePerSemester(1);
        Course course1 = curriculum.getCourseByCode("CSE1241");
        Course course2 = curriculum.getCourseByCode("MBG1201");
        ArrayList<Course> courses = new ArrayList<>();
        courses.add(course1);
        courses.add(course2);
        student.enroll(courses,curriculum,parameters);
        assertEquals(1, student.getEnrolledCourses().size());
        assertEquals(student.getEnrolledCourses().get(0), course1.getName());
    }

    @Test
    public void testCourseWhenPrerequisiteNotPassed(){
        Course course1 = curriculum.getCourseByCode("CSE1241");
        Course course2 = curriculum.getCourseByCode("CSE1242");
        ArrayList<Course> courses = new ArrayList<>();
        courses.add(course1);
        courses.add(course2);
        student.enroll(courses,curriculum,parameters);
        assertEquals(1, student.getEnrolledCourses().size());
        assertEquals(student.getEnrolledCourses().get(0), course1.getName());
    }
}
