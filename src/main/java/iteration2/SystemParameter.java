package iteration2;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class SystemParameter {

    private int semester;
    private int studentPerSemester;
    private int maxCoursePerSemester;
    private int maxCreditPerSemester;

    public SystemParameter(){

    }

    public SystemParameter(int semester, int studentPerSemester, int maxCoursePerSemester, int maxCreditPerSemester) {
        this.semester = semester;
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
                ", studentPerSemester=" + studentPerSemester +
                ", maxCoursePerSemester=" + maxCoursePerSemester +
                ", maxCreditPerSemester=" + maxCreditPerSemester +
                '}';
    }
}
