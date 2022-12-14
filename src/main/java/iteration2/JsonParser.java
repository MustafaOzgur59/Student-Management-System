package iteration2;

import com.fasterxml.jackson.core.util.DefaultIndenter;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class JsonParser {
    private static final Logger logger = LogManager.getLogger(JsonParser.class);
    private final ObjectMapper mapper = new ObjectMapper();
    private final DefaultPrettyPrinter prettyPrinter = new DefaultPrettyPrinter();

    {
        this.prettyPrinter.indentArraysWith(DefaultIndenter.SYSTEM_LINEFEED_INSTANCE);
        mapper.setDefaultPrettyPrinter(prettyPrinter);
    }

    public ArrayList<Course> parseCourseObjects(Curriculum curriculum, Instructor[] instructors) throws IOException {
        ArrayList<Course> allCourses = new ArrayList<>();
        File dirPath = new File("./src/main/java/courses");
        File[] courseFiles = dirPath.listFiles();
        for (File f : courseFiles) {
            JsonNode jsonNode = mapper.readTree(f);
            for (JsonNode courseNode : jsonNode) {
                Course course;
                ArrayNode prerequisiteArray = (ArrayNode) courseNode.get("prerequisiteTo");
                ArrayNode courseSectionArray = (ArrayNode) courseNode.get("course sessions");
                ArrayNode labSectionArray = (ArrayNode) courseNode.get("lab sessions");
                ArrayList<String> prerequisiteList = new ArrayList<>();
                ArrayList<Section> courseSectionList = new ArrayList<>();
                ArrayList<Section> labSectionList = new ArrayList<>();
                for (JsonNode prerequisite : prerequisiteArray) {
                    prerequisiteList.add(prerequisite.asText());
                }
                for (JsonNode courseSection : courseSectionArray) {
                    courseSectionList.add(new Section(courseSection.get("day").asText(), courseSection.get("hour").asText()));
                }
                for (JsonNode labSection : labSectionArray) {
                    labSectionList.add(new Section(labSection.get("day").asText(), labSection.get("hour").asText()));
                }
                if (courseNode.get("type").asText().equals("MD") || courseNode.get("code").asText().equals("TExxx")) {
                    course = new MandatoryCourse(
                            courseNode.get("name").asText(),
                            courseNode.get("code").asText(),
                            courseNode.get("term").asInt(),
                            courseNode.get("year").asInt(),
                            courseNode.get("credit").asInt(),
                            courseNode.get("quota").asInt(),
                            prerequisiteList,
                            courseSectionList,
                            labSectionList
                    );
                } else {
                    course = new TechnicalElective(
                            courseNode.get("name").asText(),
                            courseNode.get("code").asText(),
                            courseNode.get("term").asInt(),
                            courseNode.get("year").asInt(),
                            courseNode.get("credit").asInt(),
                            courseNode.get("quota").asInt(),
                            prerequisiteList,
                            courseSectionList,
                            labSectionList
                    );
                }
                allCourses.add(course);
                logger.info("Parsed course : " + course);
            }
        }
        for (Course c : allCourses) {
            if (c instanceof TechnicalElective && !c.getCode().equals("TExxx")) {
                curriculum.getTE_COURSES().add(c);
            } else {
                curriculum.getCOURSES()[(c.getYear() - 1) * 2 + c.getTerm() - 1].add(c);
            }
            Random random = new Random();
            int rand = random.nextInt(instructors.length);
            instructors[rand].getCoursesOfferedList().add(c);
            c.setInstructor(instructors[rand]);
        }
        return allCourses;
    }

    public ArrayList<Advisor> parseAdvisors(Department manager) throws IOException {
        try {
            ArrayList<Advisor> advisorList = new ArrayList<>();
            File dirPath = new File("./src/main/java/Advisors.json");
            FileInputStream inputStream = new FileInputStream(dirPath);
            JsonNode advisorArray = mapper.readTree(dirPath);
            for (JsonNode jsonNode : advisorArray){
                Advisor advisor = new Advisor(jsonNode.get("name").asText());
                advisorList.add(advisor);
            }
            inputStream.close();
            manager.setAdvisorList(advisorList);
            logger.info("Parsed advisors");
            return advisorList;
        } catch (FileNotFoundException ex){
            ex.printStackTrace();
            return null;
        }
    }

    public void outputStudentObjects(List<Student> studentList, String path) throws IOException {
        SimpleModule module = new SimpleModule();
        module.addSerializer(Student.class, new StudentSerializer());
        mapper.registerModule(module);
        for (Student s : studentList) {
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