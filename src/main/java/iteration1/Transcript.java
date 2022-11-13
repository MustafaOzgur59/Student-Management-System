package iteration1;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class Transcript {

    private Float gpa;
    @JsonProperty("semesters")
    private List<StudentSemester> semesters;
    private Integer cumulativeCredit;

    public Transcript () {
        semesters = new ArrayList<StudentSemester>();
    }



    public void calculateGpa() {
        calculateCumulativeCredit();
        float grade = 0;
        for (int i = 0; i < semesters.size(); i++) {
            grade += semesters.get(i).getYano() * semesters.get(i).getCompletedCredit();
        }
        gpa = (float) (((int)((grade/cumulativeCredit) * 100)) / 100.0);
    }

    public void calculateCumulativeCredit() {
        cumulativeCredit = 0;
        for (int i = 0; i < semesters.size(); i++) {
            cumulativeCredit += semesters.get(i).getCompletedCredit();
        }
    }

    public Float getGpa() {
        return gpa;
    }

    public void setGpa(Float gpa) {
        this.gpa = gpa;
    }

    public List<StudentSemester> getSemesters() {
        return semesters;
    }

    public void setSemesters(List<StudentSemester> semesters) {
        this.semesters = semesters;
    }


    @Override
    public String toString() {
        return "Transcript{" +
                "gpa=" + gpa +
                ", semesters=" + semesters +
                ", cumulativeCredit=" + cumulativeCredit +
                '}';
    }
}