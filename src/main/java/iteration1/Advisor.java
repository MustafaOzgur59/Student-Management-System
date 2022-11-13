package iteration1;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Advisor extends FacultyMember {

    public Advisor(String name, String id) {
        super(name, id);
    }


    @Override
    public void gradeStudents(Student student, Course course) {

    }

    public  boolean enrollStudent(Course course, Student student,Curriculum curriculum){
        int passedPrerequisiteCount=0;
        // if the student tries to take a course in the two upper semester return false
        if((( (course.getYear()-1) * 2 + course.getTerm() ) - student.getTerm()) >= 2) {
            student.getLogs().add("Cant add course: " + course.getName() + " because of semester difference of >= 2");
            java.lang.System.out.println("Cant add course: " + course.getName() + " because of semester difference of >= 2");
            return false;
        }

        //if no prerequisite is need for the course.
        if(course.getPrerequisiteTo().size() == 0){//if no prerequisite course
            student.getLogs().add("Added course : " + course.getName() + "because of no prerequisites");
            java.lang.System.out.println("Added course : " + course.getName() + "because of no prerequisites");
            return true;
        }
        else{
            // checks every prerequisite for the course
            for(int i = 0;i < course.getPrerequisiteTo().size();i++) {
                String prerequisiteCode = course.getPrerequisiteTo().get(i);
                for (StudentSemester semester: student.getTranscript().getSemesters()){
                    for (GivenCourse givenCourse: semester.getGivenCourses()){
                        if (givenCourse.getCourseCode().equals(prerequisiteCode)){
                            passedPrerequisiteCount++;
                        }
                    }
                }
            }
        }

        if (passedPrerequisiteCount == course.getPrerequisiteTo().size()){
            student.getLogs().add("Added course:"+ course.getName() +" because student passed every prerequiste");
            java.lang.System.out.println("Added course:"+ course.getName() +" because student passed every prerequiste");
            return true;
        }
        else{
            student.getLogs().add("Cant add course: " +course.getName() + " because student didnt pass a prerequisite");
            java.lang.System.out.println("Cant add course: " +course.getName() + " because student didnt pass a prerequisite");
            return false;
        }
    }


}
