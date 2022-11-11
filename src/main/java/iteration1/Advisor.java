package iteration1;

public class Advisor extends FacultyMember {

    public Advisor(String name, String id) {
        super(name, id);
    }

    @Override
    public void gradeStudents(Student student, Course course) {

    }

    public static boolean enrollStudent(Course course, Student student){

        int x = course.getPrerequisiteTo().size();
        int y = 0;
        if(course.getPrerequisiteTo().size() == 0){//if no prerequisite course
            return true;
        }
        else{
            for(int i = 0;i < course.getPrerequisiteTo().size();i++) {
                for (Course key : student.getStudentSemester().getCourses().keySet()) {
                    //We checked that he passed the prerequisite course from the Student Semester class.
                    String s = key.getCode();
                    Float f = student.getStudentSemester().getCourses().get(key);
                    if(course.getPrerequisiteTo().get(i) == s){
                        y++;
                        if(f >= 1){
                            continue;
                        }
                        else{
                            return false;
                        }
                    }

                }
            }

            }
        if(x > y){//if he has never taken the prerequisite course
            return false;
        }
        else
            return true;
    }



}
