package iteration1;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GivenCourse {
    @JsonProperty("code")
    private String courseCode;
    @JsonProperty("grade")
    private float grade;

    public GivenCourse(String courseCode, float grade) {
        this.courseCode = courseCode;
        this.grade = grade;
    }

    public GivenCourse() {
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public float getGrade() {
        return grade;
    }

    public void setGrade(float grade) {
        this.grade = grade;
    }

    @Override
    public String toString() {
        return "GivenCourses{" +
                "courseCode='" + courseCode + '\'' +
                ", grade=" + grade +
                '}';
    }
}
