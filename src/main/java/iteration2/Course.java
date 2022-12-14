package iteration2;

import com.fasterxml.jackson.annotation.*;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;


public class Course {

    private String name;
    private String code;
    private Integer term;
    private Integer year;
    private Integer credit;
    private Integer quota;
    private ArrayList<Section> courseSessions;
    private ArrayList<Section> labSessions;
    private ArrayList<String> prerequisiteTo;
    private Instructor instructor;
    private ArrayList<String> enrolledStudents = new ArrayList<>();
    final Logger logger = LogManager.getLogger(Course.class);

    public Course(String name, String code, Integer term, Integer year, Integer credit,
                  Integer quota, ArrayList<String> prerequisiteTo, ArrayList<Section> courseSessions, ArrayList<Section> labSessions) {
        this.name = name;
        this.code = code;
        this.term = term;
        this.year = year;
        this.credit = credit;
        this.quota = quota;
        this.prerequisiteTo = prerequisiteTo;
        this.courseSessions = courseSessions;
        this.labSessions = labSessions;
    }

    public Course() {

    }


    public ArrayList<String> getEnrolledStudents() {
        return enrolledStudents;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getTerm() {
        return term;
    }

    public void setTerm(Integer term) {
        this.term = term;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getCredit() {
        return credit;
    }

    public void setCredit(Integer credit) {
        this.credit = credit;
    }

    public Integer getQuota() {
        return quota;
    }

    public void setQuota(Integer quota) {
        this.quota = quota;
    }

    public ArrayList<String> getPrerequisiteTo() {
        return prerequisiteTo;
    }

    public void setPrerequisiteTo(ArrayList<String> prerequisiteTo) {
        this.prerequisiteTo = prerequisiteTo;
    }

    public ArrayList<Section> getCourseSessions() {
        return courseSessions;
    }

    public void setCourseSessions(ArrayList<Section> courseSessions) {
        this.courseSessions = courseSessions;
    }

    public ArrayList<Section> getLabSessions() {
        return labSessions;
    }

    public void setLabSessions(ArrayList<Section> labSessions) {
        this.labSessions = labSessions;
    }

    public Instructor getInstructor() {
        return instructor;
    }

    public void setInstructor(Instructor instructor) {
        this.instructor = instructor;
    }

    public void setEnrolledStudents(ArrayList<String> enrolledStudents) {
        this.enrolledStudents = enrolledStudents;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        return Objects.equals(name, course.name) && Objects.equals(code, course.code) && Objects.equals(term, course.term) && Objects.equals(year, course.year) && Objects.equals(credit, course.credit) && Objects.equals(quota, course.quota) && Objects.equals(prerequisiteTo, course.prerequisiteTo) && Objects.equals(courseSessions, course.courseSessions) && Objects.equals(labSessions, course.labSessions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, code, term, year, credit, quota, prerequisiteTo, courseSessions, labSessions);
    }

    @Override
    public String toString() {
        return "Course{" +
                "name='" + name + '\'' +
                ", code='" + code + '\'' +
                ", term=" + term +
                ", year=" + year +
                ", credit=" + credit +
                ", quota=" + quota +
                ", prerequisiteTo=" + prerequisiteTo +
                ", courseSessions=" + courseSessions +
                ", labSessions=" + labSessions +
                '}';
    }

    public boolean checkCollision(Course studentCourse) {
        for (Section courseSection : this.getCourseSessions()) {
            for (Section stdCourseSection : studentCourse.getCourseSessions()) {
                // if the two sections are in the same day
                if (stdCourseSection.getDay().equals(courseSection.getDay())) {
                    String[] courseHours = courseSection.getHours().split(","); // 1 hour range 2 or 3
                    String[] stdCourseHours = stdCourseSection.getHours().split(",");// 1 hour range  3 or 3
                    for (String courseHour : courseHours) {
                        String[] splitHours = courseHour.split("-"); // 2 hours
                        for (String studentCourseHour : stdCourseHours) {
                            String[] studentSplitHours = studentCourseHour.split("-"); // 2 hour
                            try {
                                Date startDate = new SimpleDateFormat("HH:mm").parse(splitHours[0]);
                                Date endDate = new SimpleDateFormat("HH:mm").parse(splitHours[1]);
                                Date stdStartDate = new SimpleDateFormat("HH:mm").parse(studentSplitHours[0]);
                                Date stdEndDate = new SimpleDateFormat("HH:mm").parse(studentSplitHours[1]);
                                if ((stdStartDate.after(startDate) && stdStartDate.before(endDate))
                                        || (startDate.after(stdStartDate) && startDate.before(stdEndDate))) {
                                    return true;
                                }
                            } catch (ParseException exception) {
                                logger.error("Invalid date format" + exception.getMessage());
                            }
                        }
                    }
                }
            }
        }
        return false;
    }
}