package iteration2;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.util.DefaultIndenter;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import iteration2.*;
import iteration2.Course;
import iteration2.Curriculum;
import iteration2.Instructor;
import iteration2.Student;
import org.json.JSONObject;

import java.io.*;
import java.util.*;
import java.lang.System;

public class JsonParser {
    private final ObjectMapper mapper = new ObjectMapper();
    private final DefaultPrettyPrinter prettyPrinter = new DefaultPrettyPrinter();
    {
        this.prettyPrinter.indentArraysWith(DefaultIndenter.SYSTEM_LINEFEED_INSTANCE);
        mapper.setDefaultPrettyPrinter(prettyPrinter);
    }

    public ArrayList<iteration2.Course> parseCourseObjects(Curriculum curriculum, Instructor instructor) throws IOException {
        // Old implementation  --> Files.readAllBytes(Path.of(path));
        ArrayList<iteration2.Course> allCourses = new ArrayList<>();
        File dirPath = new File("./src/main/java/courses");
        File[] courseFiles = dirPath.listFiles();
        for (File f : courseFiles){
            FileInputStream inputStream = new FileInputStream(f);
            String jsonString = new String(inputStream.readAllBytes());
            iteration2.Course[] courses = mapper.readValue(jsonString, iteration2.Course[].class);
            ArrayList<iteration2.Course> courseList = new ArrayList<>(Arrays.asList(courses));
            for (iteration2.Course course : courseList){
                allCourses.add(course);
            }
            inputStream.close();

        }
        for (Course c : allCourses){
            curriculum.getCOURSES()[(c.getYear()-1) * 2 + c.getTerm() - 1].add(c);
            instructor.getCoursesOfferedList().add(c);
            c.setInstructor(instructor);
        }
        return  allCourses;
    }

    public ArrayList<iteration2.Student> parseStudents(StudentManager manager) throws IOException {
        ArrayList<iteration2.Student> studentList = new ArrayList<>();
        File dirPath = new File("./src/main/java/students/inputStudents");
        File[] studentFiles = dirPath.listFiles();
        assert studentFiles != null;
        for (File f : studentFiles){
            FileInputStream inputStream = new FileInputStream(f);
            String jsonString = new String(inputStream.readAllBytes());
            iteration2.Student student = mapper.readValue(jsonString, iteration2.Student.class);
            manager.getStudentList().add(student);
            inputStream.close();
            System.out.println(student.toString());
        }
        return  studentList;
    }


    /*TODO
        -Parse the resulting outputs from the student objects into json files
    */
    public void outputStudentObjects(List<iteration2.Student> studentList) throws IOException {
        File dirPath = new File("./src/main/java/students/outputStudents");
        for (iteration2.Student s : studentList){
            String jsonString = mapper.writeValueAsString(s);
            File studentJsonFile = new File("./src/main/java/students/outputStudents/" + s.getId() + ".json");
            studentJsonFile.createNewFile();
            PrintWriter writer = new PrintWriter(studentJsonFile);
            writer.write(jsonString);
            writer.close();
        }
    }

    public void outputStudentObjectsWithProblems(List<iteration2.Student> studentList) throws IOException {
        File dirPath = new File("./src/main/java/students/outputStudents");
        for (Student s : studentList){
            String jsonString = mapper.writer(prettyPrinter).writeValueAsString(s);
            /*ObjectNode jsonObject = (ObjectNode) mapper.readTree(jsonString);
            ArrayList<String> logs = new ArrayList<>();
            logs.add("Hello I am here");
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

    public SystemParameter parseParameters() throws IOException {
        FileInputStream parameterStream = new FileInputStream("./src/main/java/parameters.json");
        String parameterJsonString = new String(parameterStream.readAllBytes());
        System.out.println("Json is : " + parameterJsonString);
        SystemParameter parameterObject = mapper.readValue(parameterJsonString, SystemParameter.class);
        System.out.println(parameterObject.toString());
        return parameterObject;
    }
}