package iteration2;

import iteration1.*;
import iteration1.Course;

public class Advisor extends FacultyMember {

    public Advisor(String name, String id) {
        super(name, id);
    }


    @Override
    public void gradeStudents(Student student, iteration1.Course course) {

    }

    public  boolean enrollStudent(Course course, Student student, Curriculum curriculum){
        int passedPrerequisiteCount=0;

        //EÄŸer derste Ã¶ÄŸrenci iÃ§in yer yoksa
        if (! (course.getQuota()>course.getEnrolledStudents().size())){
            student.getLogs().add("Cant add course: " + course.getName() + " to Student : " + student.getName() +" because of course quota exceeded");
            System.out.println("Cant add course: " + course.getName() + " to Student : " + student.getName() +" because of course quota exceeded");
            return  false;
        }
        // if the student tries to take a course in the two upper semester return false
        if((( (course.getYear()-1) * 2 + course.getTerm() - 1 ) - student.getTerm()) >= 2) {
            student.getLogs().add("Cant add course: " + course.getName() + " to Student : " + student.getName() + " because of semester difference of >= 2");
            System.out.println("Cant add course: " + course.getName() + " to Student : " + student.getName() + " because of semester difference of >= 2");
            return false;
        }

        //if no prerequisite is need for the course.
        if(course.getPrerequisiteTo().size() == 0){//if no prerequisite course
            student.getLogs().add("Added course : " + course.getName() + " to Student : " + student.getName() + " because of no prerequisites");
            System.out.println("Added course : " + course.getName() + "  Student : " + student.getName() + " because of no prerequisites");
            student.getEnrolledCourses().add(course.getName());
            course.getEnrolledStudents().add(student.getId());
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
            student.getLogs().add("Added course:"+ course.getName() +" to Student : " + student.getName() +" because student passed every prerequiste");
            System.out.println("Added course:"+ course.getName() +" to Student : " + student.getName() +" because student passed every prerequiste");
            return true;
        }
        else{
            student.getLogs().add("Cant add course: " +course.getName() + " to Student : " + student.getName() +" because student didnt pass a prerequisite");
            System.out.println("Cant add course: " +course.getName() + " to Student : " + student.getName() +" because student didnt pass a prerequisite");
            return false;
        }
    }
}

