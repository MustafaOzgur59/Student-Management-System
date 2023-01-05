from random import Random

from Course import Course


class TechnicalElective(Course):

    def addStudent(self, student, curriculum, system_parameters, advisor):
        random = Random()
        course = curriculum.te_courses[random.randint(0, len(curriculum.te_courses) - 1)]
        advisor.enroll_student(self, student, curriculum, system_parameters)
