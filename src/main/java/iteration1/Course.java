package iteration1;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.ArrayList;
import java.util.Objects;

//@JsonIgnoreProperties({"course sessions","lab sessions"})
@JsonPropertyOrder({"name","code","term","year","credit","quota","course sessions","lab sessions", "prerequisiteTo"})
public class Course {
    @JsonProperty("name")
    private String name;
    @JsonProperty("code")
    private String code;
    @JsonProperty("term")
    private Integer term;
    @JsonProperty("year")
    private Integer year;
    @JsonProperty("credit")
    private Integer credit;
    @JsonProperty("quota")
    private Integer quota;
    @JsonProperty("course sessions")
    private ArrayList<Section> courseSessions;
    @JsonProperty("lab sessions")
    private ArrayList<Section> labSessions;
    private ArrayList<String> prerequisiteTo;

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

    public Course(){

    }

 /*   public Course(JSONObject jsonObject) {
        this.name = jsonObject.getString("name");
        this.code = jsonObject.getString("code");
        this.term = jsonObject.getInt("term");
        this.year = jsonObject.getInt("year");
        this.credit = jsonObject.getInt("credit");
        this.quota = jsonObject.getInt("quota");
        this.prerequisiteTo = Arrays.asList(jsonObject.getJSONArray("prerequisiteTo"));
        this.courseSessions = courseSessions;
        this.labSessions = labSessions;
    }*/

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
}