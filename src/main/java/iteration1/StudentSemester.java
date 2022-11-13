package iteration1;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@JsonIgnoreProperties(value={"letterGrades","courses"},allowGetters = true)
@JsonPropertyOrder({"semesterNo","yano","takenCredit","completedCredit","note","givenCredit"})
public class StudentSemester {
    @JsonProperty("semesterNo")
    private int semesterNo;
    @JsonProperty("yano")
    private float yano;
    @JsonProperty("takenCredit")
    private int takenCredit;
    @JsonProperty("completedCredit")
    private int completedCredit;
    @JsonProperty("note")
    private float note;
    @JsonProperty("givenCourses")
    ArrayList<GivenCourse> givenCourses;
    List<String> letterGrades;

    public StudentSemester(int semesterNo) {
        letterGrades = new ArrayList<>();
        this.semesterNo = semesterNo;
    }

    public StudentSemester(){

    }

    public void calculateYano() {
        //To calculate yano, we have to know total credit of that semester.
        calculateCredit();

        this.note=0;
        for(GivenCourse course : givenCourses){
           this.note += course.getCredit() * course.getGrade();
        }
        this.yano = this.note/completedCredit;
    }

    public void calculateCredit() {
        completedCredit = 0;
        for(GivenCourse course : givenCourses){
            completedCredit += course.getCredit();
        }
    }

    public void calculateLetterGrade() {
        for(GivenCourse course : givenCourses){
            Float aFloat = course.getGrade();
            if (aFloat == 0) {
                letterGrades.add("FF");
            } else if (aFloat == 0.5) {
                letterGrades.add("FD");
            } else if (aFloat == 1) {
                letterGrades.add("DD");
            } else if (aFloat == 1.5) {
                letterGrades.add("DC");
            } else if (aFloat == 2) {
                letterGrades.add("CC");
            } else if (aFloat == 2.5) {
                letterGrades.add("CB");
            } else if (aFloat == 3) {
                letterGrades.add("BB");
            } else if (aFloat == 3.5) {
                letterGrades.add("BA");
            } else if (aFloat == 4) {
                letterGrades.add("AA");
            }
        }

    }
    public void setYano(float yano) {
        this.yano = yano;
    }

    public float getYano() {
        return yano;
    }

    public int getTakenCredit() {
        return takenCredit;
    }

    public void setTakenCredit(int takenCredit) {
        this.takenCredit = takenCredit;
    }

    public int getCompletedCredit() {
        return completedCredit;
    }

    public void setCompletedCredit(int completedCredit) {
        this.completedCredit = completedCredit;
    }

    public void setNote(float note) {
        this.note = note;
    }

    public float getNote() {
        return note;
    }

    public int getSemesterNo() {
        return semesterNo;
    }

    public void setSemesterNo(int semesterNo) {
        this.semesterNo = semesterNo;
    }

    public ArrayList<GivenCourse> getGivenCourses() {
        return givenCourses;
    }

    public void setGivenCourses(ArrayList<GivenCourse> givenCourses) {
        this.givenCourses = givenCourses;
    }

    public List<String> getLetterGrades() {
        return letterGrades;
    }

    public void setLetterGrades(List<String> letterGrades) {
        this.letterGrades = letterGrades;
    }

    @Override
    public String toString() {
        return "StudentSemester{" +
                "semesterNo=" + semesterNo +
                ", yano=" + yano +
                ", completedCredit=" + completedCredit +
                ", note=" + note +
                ", givenCourses=" + givenCourses +
                ", letterGrades=" + letterGrades +
                '}';
    }
}

