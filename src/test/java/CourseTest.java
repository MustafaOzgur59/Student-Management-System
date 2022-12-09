import iteration2.Course;
import iteration2.Curriculum;
import iteration2.Instructor;
import iteration2.JsonParser;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.io.IOException;

public class CourseTest {
    private Curriculum curriculum;
    private JsonParser parser;
    @Before
    public void initialize(){
        parser = new JsonParser();
        curriculum = new Curriculum();
        Instructor instructor = new Instructor("instructor","123214");
        try {
            parser.parseCourseObjects(curriculum,instructor);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testCourseCollision(){
        Course course1 = curriculum.getCourseByCode("CSE1241");
        Course course2 = curriculum.getCourseByCode("MATH1001");
        // checks course collision function with 2 colliding courses
        assertTrue(course1.checkCollision(course2));
    }
}
