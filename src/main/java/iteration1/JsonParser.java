package iteration1;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.json.JSONObject;

import java.io.*;
import java.util.*;
import java.lang.System;

public class JsonParser {
    private final ObjectMapper mapper = new ObjectMapper();

    public ArrayList<Course> parseCourseObjects(Curriculum curriculum) throws IOException {
        // Old implementation  --> Files.readAllBytes(Path.of(path));
        ArrayList<Course> allCourses = new ArrayList<>();
        File dirPath = new File("./src/main/java/courses");
        File[] courseFiles = dirPath.listFiles();
        for (File f : courseFiles){
            FileInputStream inputStream = new FileInputStream(f);
            String jsonString = new String(inputStream.readAllBytes());
            Course[] courses = mapper.readValue(jsonString, Course[].class);
            ArrayList<Course> courseList = new ArrayList<>(Arrays.asList(courses));
            for (Course course : courseList){
                allCourses.add(course);
            }
            inputStream.close();
        }

        for (Course c : allCourses){
            curriculum.getCOURSES()[c.getTerm()].add(c);
            System.out.println(c.toString());
        }

        return  allCourses;
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
        -Parse the resulting outputs from the student objects into json files
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

    public void outputStudentObjectsWithProblems(List<Student> studentList) throws IOException {
        File dirPath = new File("./src/main/java/students/outputStudents");
        for (Student s : studentList){
            String jsonString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(s);
            /*ObjectNode jsonObject = (ObjectNode) mapper.readTree(jsonString);
            ArrayList<String> logs = new ArrayList<>();
            logs.add("Hello I am here");
            logs.add("DENEMEE");
            logs.add("DENEMEE");
            logs.add("DENEMEE");
            logs.add("DENEMEE");
            logs.add("DENEMEE");
            logs.add("DENEMEE");
            logs.add("DENEMEE");
            logs.add("DENEMEE");
            logs.add("DENEMEE");
            logs.add("DENEMEE");
            logs.add("DENEMEE");
            logs.add("DENEMEE");
            logs.add("DENEMEE");
            logs.add("DENEMEE");
            logs.add("DENEMEE");
            JsonNode arrayNode = mapper.valueToTree(logs);
            jsonObject.set("logs",arrayNode);
            jsonString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonObject);
            String newString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonObject);*/
            File studentJsonFile = new File("./src/main/java/students/outputStudents/" + s.getId() + ".json");
            studentJsonFile.createNewFile();
            PrintWriter writer = new PrintWriter(studentJsonFile);
            writer.write(jsonString);
            writer.close();
        }
    }
}