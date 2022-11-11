package iteration1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.lang.System;


public class StudentGenerator {
    ArrayList<Student> studentList= new ArrayList<>();

    public ArrayList<Student> generateStudents() throws IOException {
        BufferedReader nameReader = new BufferedReader(new FileReader("C:\\Users\\Mustafa\\Desktop\\deneme\\CSE3063F22P1_GRP4\\src\\main\\java\\iteration1\\names.txt"));
        for (int i=1;i<=70;i++){
            String name = nameReader.readLine();
            studentList.add(new Student(this.generateId( 1 , i),
                    name,1));
        }

        for (Student s : studentList){
            System.out.println(s.toString());
        }
        return this.studentList;
    }

    private String generateId(int year,int entryPlace){
        String place = Integer.toString(entryPlace);
        if (place.length() == 1) place = "0" + place;
        String departmentNo = "1501";
        String entryYear = Integer.toString( 20 + year );
        return departmentNo+entryYear+place;
    }
}
