package iteration1;

import java.io.IOException;
import java.lang.System;

public class Main {
    public static void main(String[] args) throws IOException {
        RegistirationSystem registirationSystem = new RegistirationSystem();
        registirationSystem.loadStudentAndCourses();
        registirationSystem.beginSimulation();
        for (Student s : registirationSystem.getStudentManager().getStudentList()){
            System.out.println(s.toString());
        }
    }
}
