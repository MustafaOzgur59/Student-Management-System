package iteration1;

import java.util.ArrayList;

public class Curriculum {
    private final ArrayList<Course>[] COURSES ;

    public Curriculum() {
        COURSES = new ArrayList[8];
        for(int i=0; i<8;i++){
            COURSES[i] = new ArrayList<>();
        }
    }

    public ArrayList<Course>[] getCOURSES() {
        return COURSES;
    }

    public Course getCourse(String courseName){
        for (ArrayList<Course> courses : COURSES){
            for (Course c : courses){
                if (c.getName().equals(courseName)){
                    return c;
                }
            }
        }
        return null;
    }
}