package iteration2;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class Advisor extends Instructor {
    private static final Logger logger = LogManager.getLogger(Advisor.class);

    public Advisor(String name) {
        super(name, "advisor");
    }


    public  boolean enrollStudent(Course course, Student student,Curriculum curriculum,SystemParameter systemParameters){
        //check if student enrolled more than allowed amount of course
        if(checkAmountOfCourse(course,student,student.getEnrolledCourses().size(),systemParameters.getMaxCoursePerSemester()))
            return  false;

        //check if student exceeded allowable credit limit
        if(checkCreditLimit(course,student,student.getStudentSemester().getTakenCredit(),systemParameters.getMaxCreditPerSemester()))
            return  false;

        //if course quota is full
        if(checkQuota(course,student,course.getQuota(),course.getEnrolledStudents().size()))
            return  false;

        // if the student tries to take a course in the two upper semester return false
        if(checkIfUpper(course,student,course.getYear(),course.getTerm(),student.getTerm()))
            return false;

        // if no course sessions overlap with this course's sessions
        if(checkCourseCollision(student,course,curriculum))
            return false;

        //if no prerequisite is need for the course.
        if(hasPrerequisite(course,student))//if no prerequisite course
            return true;
        else{
            if (checkPrerequisite(course,student))
                return true;
            else
                return false;
        }
    }

    public boolean checkCourseCollision(Student student,Course course,Curriculum curriculum){
        for (String courseName : student.getEnrolledCourses()){
            Course stdCourse = curriculum.getCourse(courseName);
             if(course.checkCollision(stdCourse)){
                 student.getLogs().add("Cant add course: " + course.getName() + " to Student : " + student.getName() + " because of course hour collisions");
                 logger.info("Cant add course: " + course.getName() + " to Student : " + student.getName() + " because of course hour collisions");
                 return true;
             }
        }
        return false;
    }

    public boolean checkAmountOfCourse(Course course, Student student,int size, int max){
        if (size >= max) {
            student.getLogs().add("Cant add course: " + course.getName() + " to Student : " + student.getName() + " because of maximum allowed course amount exceeded");
            logger.info("Cant add course: " + course.getName() + " to Student : " + student.getName() + " because of maximum allowed course amount exceeded");
            return true;
        }
        return false;
    }

    public boolean checkCreditLimit(Course course, Student student, int takenCredit, int max){
        if(takenCredit>=max){
            student.getLogs().add("Cant add course: " + course.getName() + " to Student : " + student.getName() +" because of maximum allowed credit amount exceeded");
            logger.info("Cant add course: " + course.getName() + " to Student : " + student.getName() +" because of maximum allowed credit amount exceeded");
            return true;
        }
        return false;
    }
    public boolean checkQuota(Course course, Student student,int quota, int stdNum){
        if(quota <= stdNum){
            student.getLogs().add("Cant add course: " + course.getName() + " to Student : " + student.getName() +" because of course quota exceeded");
            logger.info("Cant add course: " + course.getName() + " to Student : " + student.getName() +" because of course quota exceeded");
            return true;
        }
        return false;
    }
    public boolean checkIfUpper(Course course, Student student, int cyear, int cterm, int sterm){
        if((cyear-1)*2 + cterm -sterm >=2){
            student.getLogs().add("Cant add course: " + course.getName() + " to Student : " + student.getName() + " because of semester difference of >= 2");
            logger.info("Cant add course: " + course.getName() + " to Student : " + student.getName() + " because of semester difference of >= 2");
            return true;
        }
        return false;
    }
    public boolean hasPrerequisite(Course course, Student student){
        if(course.getPrerequisiteTo().size()==0){
            student.getLogs().add("Added course : " + course.getName() + " to Student : " + student.getName() + " because of no prerequisites");
            logger.info("Added course : " + course.getName() + "  Student : " + student.getName() + " because of no prerequisites");
            student.getEnrolledCourses().add(course.getName());
            student.getStudentSemester().setTakenCredit(student.getStudentSemester().getTakenCredit() + course.getCredit());
            course.getEnrolledStudents().add(student.getId());
            return true;
        }
        return false;
    }

    public boolean checkPrerequisite(Course course, Student student){
        // checks every prerequisite for the course
        int passedPrerequisiteCount = 0;
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
        if (passedPrerequisiteCount == course.getPrerequisiteTo().size()){
            student.getLogs().add("Added course:"+ course.getName() +" to Student : " + student.getName() +" because student passed every prerequiste");
            logger.info("Added course:"+ course.getName() +" to Student : " + student.getName() +" because student passed every prerequiste");
            student.getEnrolledCourses().add(course.getName());
            student.getStudentSemester().setTakenCredit(student.getStudentSemester().getTakenCredit() + course.getCredit());
            course.getEnrolledStudents().add(student.getId());
            return true;
        }
        else{
            student.getLogs().add("Cant add course: " +course.getName() + " to Student : " + student.getName() +" because student didnt pass a prerequisite");
            logger.info("Cant add course: " +course.getName() + " to Student : " + student.getName() +" because student didnt pass a prerequisite");
            return false;
        }
    }
}

