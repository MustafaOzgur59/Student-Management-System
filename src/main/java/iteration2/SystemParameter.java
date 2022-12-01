package iteration2;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class SystemParameter {
    @JsonProperty("semester")
    private int semester;
    @JsonProperty("passRatio")
    private int passRatio;
    @JsonProperty("studentPerSemester")
    private int studentPerSemester;
    @JsonProperty("maxCoursePerSemester")
    private int maxCoursePerSemester;
    @JsonProperty("maxCreditPerSemester")
    private int maxCreditPerSemester;

    @JsonCreator
    public SystemParameter(
            @JsonProperty("semester") int semester,
            @JsonProperty("passRatio") int passRatio,
            @JsonProperty("studentPerSemester")int studentPerSemester,
            @JsonProperty("maxCoursePerSemester")int maxCoursePerSemester,
            @JsonProperty("maxCreditPerSemester")int maxCreditPerSemester) {
        this.semester = semester;
        this.passRatio = passRatio;
        this.studentPerSemester= studentPerSemester;
        this.maxCoursePerSemester=maxCoursePerSemester;
        this.maxCreditPerSemester=maxCreditPerSemester;
    }

    public int getSemester() {
        return semester;
    }

    public void setSemester(int semester) {
        this.semester = semester;
    }

    public int getPassRatio() {
        return passRatio;
    }

    public void setPassRatio(int passRatio) {
        this.passRatio = passRatio;
    }

    public int getStudentPerSemester() {
        return studentPerSemester;
    }

    public void setStudentPerSemester(int studentPerSemester) {
        this.studentPerSemester = studentPerSemester;
    }

    public int getMaxCoursePerSemester() {
        return maxCoursePerSemester;
    }

    public void setMaxCoursePerSemester(int maxCoursePerSemester) {
        this.maxCoursePerSemester = maxCoursePerSemester;
    }

    public int getMaxCreditPerSemester() {
        return maxCreditPerSemester;
    }

    public void setMaxCreditPerSemester(int maxCreditPerSemester) {
        this.maxCreditPerSemester = maxCreditPerSemester;
    }

    @Override
    public String toString() {
        return "SystemParameter{" +
                "semester=" + semester +
                ", passRatio=" + passRatio +
                ", studentPerSemester=" + studentPerSemester +
                ", maxCoursePerSemester=" + maxCoursePerSemester +
                ", maxCreditPerSemester=" + maxCreditPerSemester +
                '}';
    }
}
