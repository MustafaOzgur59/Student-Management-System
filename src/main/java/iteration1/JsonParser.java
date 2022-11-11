package iteration1;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.lang.System;
public class JsonParser {
    private final ObjectMapper mapper = new ObjectMapper();
    
    public ArrayList<Course> parseCourseObjects() throws IOException {
        String jsonString = new String(Files.readAllBytes(Path.of("./src/main/java/courses/CourseFirstYear.json")));
        Course[] courses = mapper.readValue(jsonString, Course[].class);
        ArrayList<Course> courseList = new ArrayList<>(Arrays.asList(courses));
        for (Course c : courseList){
            System.out.println(c.toString());
        }
        return  courseList;
    }
}