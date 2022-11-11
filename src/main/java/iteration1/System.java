package iteration1;

public class System {
    private StudentManager studentManager = new StudentManager();

    private JsonParser parser = new JsonParser();



    public System(StudentManager studentManager, JsonParser parser) {
        this.studentManager = studentManager;
        this.parser = parser;
    }

    public StudentManager getStudentManager() {
        return studentManager;
    }

    public void setStudentManager(StudentManager studentManager) {
        this.studentManager = studentManager;
    }

    public JsonParser getParser() {
        return parser;
    }

    public void setParser(JsonParser parser) {
        this.parser = parser;
    }
}
