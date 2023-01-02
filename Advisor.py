import logging

from iteration3 import Course
from iteration3 import Student
from iteration3 import Curriculum
from iteration3 import SystemParameter

logger = logging.getLogger(__name__)

class Advisor(Instructor):
    def __init__(self, name: str):
        super().__init__(name, "advisor")

    def enroll_student(self, course: Course, student: Student, curriculum: Curriculum, system_parameters: SystemParameter) -> bool:
        # check if student enrolled more than allowed amount of course
        if self.check_amount_of_course(course, student, len(student.enrolled_courses), system_parameters.max_course_per_semester):
            return False

        # check if student exceeded allowable credit limit
        if self.check_credit_limit(course, student, student.student_semester.taken_credit, system_parameters.max_credit_per_semester):
            return False

        # if course quota is full
        if self.check_quota(course, student, course.quota, len(course.enrolled_students)):
            return False

        # if the student tries to take a course in the two upper semester return false
        if self.check_if_upper(course, student, course.year, course.term, student.term):
            return False

        # if no course sessions overlap with this course's sessions
        if self.check_course_collision(student, course, curriculum):
            return False

        # if no prerequisite is need for the course.
        if self.has_prerequisite(course, student):  # if no prerequisite course
            return True
        else:
            if self.check_prerequisite(course, student):
                return True
            else:
                return False

    def check_course_collision(self, student: Student, course: Course, curriculum: Curriculum) -> bool:
        for course_name in student.enrolled_courses:
            std_course = curriculum.get_course(course_name)
            if course.check_collision(std_course):
                student.logs.append(f"Cant add course: {course.name} to Student : {student.name} because of course hour collisions")
                logger.info(f"Cant add course: {course.name} to Student : {student.name} because of course hour collisions")
                return True
        return False

    def check_amount_of_course(self, course: Course, student: Student, size: int, max: int) -> bool:
        if size >= max:
            student.logs.append(f"Cant add course: {course.name} to Student : {student.name} because of maximum allowed course amount exceeded")
            logger.info(f"Cant add course: {course.name} to Student : {student.name} because of maximum allowed course amount exceeded")
            return True
        return False

    def check_credit_limit(self, course: Course, student: Student, taken_credit: int, max: int) -> bool:
        if taken_credit >= max:
            student.logs.append(f"Cant add course: {course.name} to Student : {student.name} because of maximum allowed credit amount exceeded")
            logger.info(f"Cant
            return True
        return False
    def check_quota(self, course: Course, student: Student, quota: int, std_num: int) -> bool:
        if quota <= std_num:
            student.logs.append(f"Cant add course: {course.name} to Student : {student.name} because of course quota exceeded")
            logger.info(f"Cant add course: {course.name} to Student : {student.name} because of course quota exceeded")
            return True
        return False

    def check_if_upper(self, course: Course, student: Student, year: int, term: int, std_term: int) -> bool:
        if year < std_term:
            student.logs.append(f"Cant add course: {course.name} to Student : {student.name} because of trying to take upper semester course")
            logger.info(f"Cant add course: {course.name} to Student : {student.name} because of trying to take upper semester course")
            return True
        elif year == std_term and term < 3:
            student.logs.append(f"Cant add course: {course.name} to Student : {student.name} because of trying to take upper semester course")
            logger.info(f"Cant add course: {course.name} to Student : {student.name} because of trying to take upper semester course")
            return True
        return False

    def has_prerequisite(self, course: Course, student: Student) -> bool:
        return course.prerequisites == []

    def check_prerequisite(self, course: Course, student: Student) -> bool:
        for prereq in course.prerequisites:
            if prereq not in student.enrolled_courses:
                student.logs.append(f"Cant add course: {course.name} to Student : {student.name} because of missing prerequisite course: {prereq}")
                logger.info(f"Cant add course: {course.name} to Student : {student.name} because of missing prerequisite course: {prereq}")
                return False
        return True

