package iteration2;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class SystemParameter {
    @JsonProperty("semester")
    private int semester;
    @JsonProperty("passRatio")
    private int passRatio;

    @JsonCreator
    public SystemParameter(
            @JsonProperty("semester") int semester,
            @JsonProperty("passRatio") int passRatio) {
        this.semester = semester;
        this.passRatio = passRatio;
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

    @Override
    public String toString() {
        return "SystemParameter{" +
                "semester=" + semester +
                ", passRatio=" + passRatio +
                '}';
    }
}
