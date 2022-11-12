package iteration1;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.lang.System;
public class JsonParser {
    private final ObjectMapper mapper = new ObjectMapper();

    public ArrayList<Course> parseCourseObjects(Curriculum curriculum) throws IOException {
        // Old implementation  --> Files.readAllBytes(Path.of(path));
        FileInputStream inputStream = new FileInputStream("./src/main/java/courses/CourseFirstYear.json");
        String jsonString = new String(inputStream.readAllBytes());
        Course[] courses = mapper.readValue(jsonString, Course[].class);
        ArrayList<Course> courseList = new ArrayList<>(Arrays.asList(courses));
        for (Course c : courseList){
            curriculum.getCOURSES()[c.getTerm()].add(c);
            System.out.println(c.toString());
        }
        inputStream.close();
        return  courseList;
    }

    public ArrayList<Student> parseStudents() throws IOException {
        ArrayList<Student> studentList = new ArrayList<>();
        File dirPath = new File("./src/main/java/students/");
        File[] studentFiles = dirPath.listFiles();
        assert studentFiles != null;
        for (File f : studentFiles){
            FileInputStream inputStream = new FileInputStream(f);
            String jsonString = new String(inputStream.readAllBytes());
            Student student = mapper.readValue(jsonString,Student.class);
            inputStream.close();
            System.out.println(student.toString());
        }
        /*String jsonString = new String(Files.readAllBytes(Path.of("./src/main/java/students/150121017.json")));
        Student student  = mapper.readValue(jsonString, Student.class);
        System.out.println(student.toString());*/
        return  studentList;
    }


    /*TODO
    Parse the resulting outputs from the student objects into json files
    */
}