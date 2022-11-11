package iteration1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class StudentSemester {
    private int semesterNo;
    private float yano;
    private int totalCredit;
    private float note;
    HashMap<Course, Float> courses;
    List<String> letterGrades;

    public StudentSemester(int semesterNo) {
        courses = new HashMap<Course, Float>();
        letterGrades = new ArrayList<>();
        this.semesterNo = semesterNo;
    }

    public void calculateYano() {
        //To calculate yano, we have to know total credit of that semester.
        calculateCredit();

        this.note=0;
        for(Course key : courses.keySet()){
           this.note += key.getCredit() * courses.get((Course)key);
        }
        this.yano = this.note/totalCredit;
    }

    public void calculateCredit() {
        totalCredit = 0;
        for(Course key : courses.keySet()){
            totalCredit += key.getCredit();
        }
    }

    public void calculateLetterGrade() {
        for(Course key : courses.keySet()){
            Float aFloat = courses.get(key);
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

    public void setTotalCredit(int totalCredit) {
        this.totalCredit = totalCredit;
    }

    public int getTotalCredit() {
        return totalCredit;
    }

    public void setNote(float note) {
        this.note = note;
    }

    public float getNote() {
        return note;
    }

    public void setCourses(HashMap<Course, Float> courses) {
        this.courses = courses;
    }

    public HashMap<Course, Float> getCourses() {
        return courses;
    }


}

