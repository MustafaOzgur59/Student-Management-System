package iteration2;

import com.fasterxml.jackson.core.util.DefaultIndenter;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.*;
import java.util.*;
import java.lang.System;

public class JsonParser {
    private static final Logger logger = LogManager.getLogger(JsonParser.class);
    private final ObjectMapper mapper = new ObjectMapper();
    private final DefaultPrettyPrinter prettyPrinter = new DefaultPrettyPrinter();
    {
        this.prettyPrinter.indentArraysWith(DefaultIndenter.SYSTEM_LINEFEED_INSTANCE);
        mapper.setDefaultPrettyPrinter(prettyPrinter);
    }

    public ArrayList<iteration2.Course> parseCourseObjects(Curriculum curriculum, Instructor[] instructors) throws IOException {
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
                logger.info("Parsed course : " + course.getName());
            }
            inputStream.close();

        }
        for (Course c : allCourses){
            /*if (c instanceof TechnicalElective && !c.getCode().equals("TExxx")){
                curriculum.getTE_COURSES().add(c);
            }
            else{*/
                curriculum.getCOURSES()[(c.getYear()-1) * 2 + c.getTerm() - 1].add(c);
            //}
            Random random = new Random();
            int rand = random.nextInt(instructors.length);
            instructors[rand].getCoursesOfferedList().add(c);
            c.setInstructor(instructors[rand]);
        }
        return  allCourses;
    }

    public ArrayList<Advisor> parseAdvisors(Department manager) throws IOException {
        File dirPath = new File("./src/main/java/Advisors.json");
        FileInputStream inputStream = new FileInputStream(dirPath);
        String jsonString = new String(inputStream.readAllBytes());
        Advisor[] advisors = mapper.readValue(jsonString, Advisor[].class);
        logger.info("Created advisors");
        inputStream.close();
        ArrayList<Advisor> advisorList = new ArrayList<>(Arrays.asList(advisors));
        manager.setAdvisorList(advisorList);
        return advisorList;
    }

    public ArrayList<iteration2.Student> parseStudents(Department manager) throws IOException {
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


    public void outputStudentObjectsWithProblems(List<iteration2.Student> studentList,String path) throws IOException {
        File dirPath = new File(path);
        for (Student s : studentList){
            String jsonString = mapper.writer(prettyPrinter).writeValueAsString(s);
            File studentJsonFile = new File(path + s.getId() + ".json");
            studentJsonFile.createNewFile();
            PrintWriter writer = new PrintWriter(studentJsonFile);
            writer.write(jsonString);
            writer.close();
        }
    }

    public SystemParameter parseParameters() throws IOException {
        FileInputStream parameterStream = new FileInputStream("./src/main/java/parameters.json");
        String parameterJsonString = new String(parameterStream.readAllBytes());
        SystemParameter parameterObject = mapper.readValue(parameterJsonString, SystemParameter.class);
        logger.info("Parsed system parameters : " + parameterObject.toString());

        return parameterObject;
    }
}