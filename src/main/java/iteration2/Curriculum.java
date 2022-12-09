package iteration2;

import java.util.ArrayList;

public class Curriculum {
    private final ArrayList<Course>[] COURSES ;
    private final ArrayList<Course> TE_COURSES;

    public Curriculum() {
        TE_COURSES = new ArrayList<>();
        COURSES = new ArrayList[8];
        for(int i=0; i<8;i++){
            COURSES[i] = new ArrayList<>();
        }
    }

    public ArrayList<Course>[] getCOURSES() {
        return COURSES;
    }

    public ArrayList<Course> getTE_COURSES() {
        return TE_COURSES;
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

    public Course getCourseByCode(String courseCode){
        for (ArrayList<Course> courses : COURSES){
            for (Course c : courses){
                if (c.getCode().equals(courseCode)){
                    return c;
                }
            }
        }
        return null;
    }
}