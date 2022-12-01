package iteration2;

import iteration1.Course;

import java.util.ArrayList;

public class Curriculum {
    private final ArrayList<iteration1.Course>[] COURSES ;

    public Curriculum() {
       COURSES = new ArrayList[8];
       for(int i=0; i<8;i++){
           COURSES[i] = new ArrayList<>();
       }
    }

    public ArrayList<Course>[] getCOURSES() {
        return COURSES;
    }


}
