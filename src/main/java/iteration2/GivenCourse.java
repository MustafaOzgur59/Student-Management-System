package iteration2;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class GivenCourse {

    private String courseCode;

    private float grade;

    private String letterGrade;

    private Integer credit;

    public GivenCourse(String courseCode, float grade, Integer credit) {
        this.courseCode = courseCode;
        this.grade = grade;
        this.credit = credit;
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

    public Integer getCredit() {
        return credit;
    }

    public String getLetterGrade() {
        return letterGrade;
    }

    public void setLetterGrade(String letterGrade) {
        this.letterGrade = letterGrade;
    }

    public void setCredit(Integer credit) {
        this.credit = credit;
    }

    public void calculateLetterGrade() {

            Float aFloat = this.grade;
            if (aFloat == 0) {
                this.letterGrade = "FF";
            } else if (aFloat == 0.5) {
                this.letterGrade = "FD";
            } else if (aFloat == 1) {
                this.letterGrade = "DD";
            } else if (aFloat == 1.5) {
                this.letterGrade = "DC";
            } else if (aFloat == 2) {
                this.letterGrade = "CC";
            } else if (aFloat == 2.5) {
                this.letterGrade = "CB";
            } else if (aFloat == 3) {
                this.letterGrade = "BB";
            } else if (aFloat == 3.5) {
                this.letterGrade = "BA";
            } else if (aFloat == 4) {
                this.letterGrade = "AA";
            }
    }

    @Override
    public String toString() {
        return "GivenCourses{" +
                "courseCode='" + courseCode + '\'' +
                ", grade=" + grade +
                '}';
    }
}
