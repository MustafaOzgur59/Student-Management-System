from Course import Course


class MandatoryCourse(Course):

    def addStudent(self, student, curriculum, system_parameters, advisor):
        advisor.enroll_student(self, student, curriculum, system_parameters)