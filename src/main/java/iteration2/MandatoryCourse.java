package iteration2;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

public class MandatoryCourse extends Course {
    private String type;

    @JsonCreator
    public MandatoryCourse(
            @JsonProperty("name") String name,
            @JsonProperty("code") String code,
            @JsonProperty("term") Integer term,
            @JsonProperty("year") Integer year,
            @JsonProperty("credit") Integer credit,
            @JsonProperty("quota") Integer quota,
            @JsonProperty("prerequisiteTo") ArrayList<String> prerequisiteTo,
            @JsonProperty("course sessions") ArrayList<Section> courseSessions,
            @JsonProperty("lab sessions") ArrayList<Section> labSessions,
            @JsonProperty("type") String type) {
        super(name, code, term, year, credit, quota, prerequisiteTo, courseSessions, labSessions);
        this.type = type;
    }

    public MandatoryCourse(String type) {
        this.type = type;
    }
}
