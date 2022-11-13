package iteration1;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        RegistirationSystem system = new RegistirationSystem();
        system.loadStudentAndCourses();
        system.beginSimulation();
        for (Student s : system.getStudentManager().getStudentList()){
            System.out.println(s.toString());
        }
    }
}
