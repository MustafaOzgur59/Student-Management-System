package iteration2;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

public class MandatoryCourse extends Course {

    public MandatoryCourse(
            String name,
            String code,
            Integer term,
            Integer year,
            Integer credit,
            Integer quota,
            ArrayList<String> prerequisiteTo,
            ArrayList<Section> courseSessions,
            ArrayList<Section> labSessions) {
        super(name, code, term, year, credit, quota, prerequisiteTo, courseSessions, labSessions);
    }

}
