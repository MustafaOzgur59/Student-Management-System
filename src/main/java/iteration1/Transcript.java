package iteration1;
import java.util.ArrayList;
import java.util.List;

public class Transcript {

    private Float gpa;
    private List<StudentSemester> semesters;
    private Integer cumulativeCredit;

    public Transcript () {
        semesters = new ArrayList<StudentSemester>();
    }

    public void calculateGpa() {
        calculateCumulativeCredit();
        float grade = 0;
        for (int i = 0; i < semesters.size(); i++) {
            grade += semesters.get(i).getYano() * semesters.get(i).getTotalCredit();
        }
        gpa = grade / cumulativeCredit;
    }

    public void calculateCumulativeCredit() {
        cumulativeCredit = 0;
        for (int i = 0; i < semesters.size(); i++) {
            cumulativeCredit += semesters.get(i).getTotalCredit();
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

}
