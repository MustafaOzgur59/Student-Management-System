package iteration2;

import iteration1.RegistrationSystem;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        RegistrationSystem registrationSystem = new RegistrationSystem();
        registrationSystem.readJsonFiles();
        registrationSystem.beginSimulation();
    }
}
