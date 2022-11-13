package iteration1;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        System system = new System();
        system.loadStudentAndCourses();
        system.beginSimulation();
        for (Student s : system.getStudentManager().getStudentList()){
            java.lang.System.out.println(s.toString());
        }
    }
}
