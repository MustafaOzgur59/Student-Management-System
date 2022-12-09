package iteration2;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class Advisor extends FacultyMember {
    private static final Logger logger = LogManager.getLogger(Advisor.class);

    @JsonCreator
    public Advisor(@JsonProperty("name") String name) {
        super(name, "advisor");
    }


    @Override
    public void gradeStudents(Student student, Course course) {

    }

    public  boolean enrollStudent(Course course, Student student,Curriculum curriculum,SystemParameter systemParameters){
        int passedPrerequisiteCount=0;

        //check if student enrolled more than allowed amount of course
        if((student.getEnrolledCourses().size()>= systemParameters.getMaxCoursePerSemester())){
            student.getLogs().add("Cant add course: " + course.getName() + " to Student : " + student.getName() +" because of maximum allowed course amount exceeded");
            logger.info("Cant add course: " + course.getName() + " to Student : " + student.getName() +" because of maximum allowed course amount exceeded");
            return  false;
        }


        //check if student exceeded allowable credit limit
        if((student.getStudentSemester().getTakenCredit()>= systemParameters.getMaxCreditPerSemester())){
            student.getLogs().add("Cant add course: " + course.getName() + " to Student : " + student.getName() +" because of maximum allowed credit amount exceeded");
            logger.info("Cant add course: " + course.getName() + " to Student : " + student.getName() +" because of maximum allowed credit amount exceeded");
            return  false;
        }

        //if course quota is full
        if (! (course.getQuota()>course.getEnrolledStudents().size())){
            student.getLogs().add("Cant add course: " + course.getName() + " to Student : " + student.getName() +" because of course quota exceeded");
            logger.info("Cant add course: " + course.getName() + " to Student : " + student.getName() +" because of course quota exceeded");
            return  false;
        }
        // if the student tries to take a course in the two upper semester return false
        if((( (course.getYear()-1) * 2 + course.getTerm()) - student.getTerm()) >= 2) {
            student.getLogs().add("Cant add course: " + course.getName() + " to Student : " + student.getName() + " because of semester difference of >= 2");
            logger.info("Cant add course: " + course.getName() + " to Student : " + student.getName() + " because of semester difference of >= 2");
            return false;
        }

        // if no course sessions overlap with this course's sessions
        if(checkCourseCollision(student,course,curriculum)){
            student.getLogs().add("Cant add course: " + course.getName() + " to Student : " + student.getName() + " because of course hour collisions");
            logger.info("Cant add course: " + course.getName() + " to Student : " + student.getName() + " because of course hour collisions");
            return false;
        }
        //if no prerequisite is need for the course.
        if(course.getPrerequisiteTo().size() == 0){//if no prerequisite course
            student.getLogs().add("Added course : " + course.getName() + " to Student : " + student.getName() + " because of no prerequisites");
            logger.info("Added course : " + course.getName() + "  Student : " + student.getName() + " because of no prerequisites");
            student.getEnrolledCourses().add(course.getName());
            student.getStudentSemester().setTakenCredit(student.getStudentSemester().getTakenCredit() + course.getCredit());
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

    public boolean checkCourseCollision(Student student,Course course,Curriculum curriculum){
        for (String courseName : student.getEnrolledCourses()){
            Course stdCourse = curriculum.getCourse(courseName);
             if(course.checkCollision(stdCourse)){
                 return true;
             }
        }
        return false;
    }
}

