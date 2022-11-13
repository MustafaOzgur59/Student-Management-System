package iteration1;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.lang.System;
import java.util.List;

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

    public ArrayList<Student> parseStudents(StudentManager manager) throws IOException {
        ArrayList<Student> studentList = new ArrayList<>();
        File dirPath = new File("./src/main/java/students/inputStudents");
        File[] studentFiles = dirPath.listFiles();
        assert studentFiles != null;
        for (File f : studentFiles){
            FileInputStream inputStream = new FileInputStream(f);
            String jsonString = new String(inputStream.readAllBytes());
            Student student = mapper.readValue(jsonString,Student.class);
            manager.getStudentList().add(student);
            inputStream.close();
            System.out.println(student.toString());
        }
        return  studentList;
    }


    /*TODO
    Parse the resulting outputs from the student objects into json files
    */
    public void outputStudentObjects(List<Student> studentList) throws IOException {
        File dirPath = new File("./src/main/java/students/outputStudents");
        for (Student s : studentList){
            String jsonString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(s);
            File studentJsonFile = new File("./src/main/java/students/outputStudents/" + s.getId() + ".json");
            studentJsonFile.createNewFile();
            PrintWriter writer = new PrintWriter(studentJsonFile);
            writer.write(jsonString);
            writer.close();
        }
    }
}