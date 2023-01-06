import logging

from Course import Course
import Curriculum
import Student
from Instructor import Instructor
from SystemParameter import SystemParameter

logger = logging.getLogger(__name__)


class Advisor(Instructor):
    def __init__(self, name: str):
        super().__init__(name, "advisor")

    def enroll_student(self, course: Course, student: Student, curriculum: Curriculum,
                       systemParameter: SystemParameter) -> bool:
        # check if student enrolled more than allowed amount of course
        if self.check_amount_of_course(course, student, len(student.enrolled_courses),
                                       systemParameter.max_course_per_semester):
            return False

        # check if student exceeded allowable credit limit
        if self.check_credit_limit(course, student, student.student_semester.taken_credit,
                                   systemParameter.max_credit_per_semester):
            return False

        # if course quota is full
        if self.check_quota(course, student, course.quota, len(course.enrolledStudents)):
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

    def check_amount_of_course(self, course: Course, student: Student, size: int, max: int) -> bool:
        if size >= max:
            student.logs.append(
                f"Cant add course: {course.name} to Student : {student.name} because of maximum allowed course amount exceeded")
            logger.info(
                f"Cant add course: {course.name} to Student : {student.name} because of maximum allowed course amount exceeded")
            return True
        return False

    def check_if_upper(self, course: Course, student: Student, year: int, term: int, std_term: int) -> bool:
        if (year - 1) * 2 + term - std_term >= 2:
            student.logs.append(
                f"Cant add course: {course.name} to Student : {student.name} because of trying to take upper semester course")
            logger.info(
                f"Cant add course: {course.name} to Student : {student.name} because of trying to take upper semester course")
            return True
        return False

    def check_course_collision(self, student: Student, course: Course, curriculum: Curriculum) -> bool:
        for course_name in student.enrolled_courses:
            std_course = curriculum.get_course(course_name)
            if course.checkCollision(std_course):
                student.logs.append(
                    f"Cant add course: {course.name} to Student : {student.name} because of course hour collisions")
                logging.info(
                    f"Cant add course: {course.name} to Student : {student.name} because of course hour collisions")
                return True
        return False

    def check_credit_limit(self, course: Course, student: Student, taken_credit: int, max: int) -> bool:
        if taken_credit >= max:
            student.logs.append(
                f"Cant add course: {course.name} to Student : {student.name} because of maximum allowed credit amount exceeded")
            logging.info(
                f"Cant add course: {course.name} to Student : {student.name} because of maximum allowed credit amount exceeded")
            return True
        return False

    def check_quota(self, course: Course, student: Student, quota: int, std_num: int) -> bool:
        if quota <= std_num:
            student.logs.append(
                f"Cant add course: {course.name} to Student : {student.name} because of course quota exceeded")
            logging.info(f"Cant add course: {course.name} to Student : {student.name} because of course quota exceeded")
            return True
        return False

    def has_prerequisite(self, course: Course, student: Student) -> bool:
        if course.prerequisiteTo == []:
            student.enrolled_courses.append(course.name)
            student.student_semester.taken_credit = student.student_semester.taken_credit + course.credit
            course.enrolledStudents.append(student.id)
            return True
        return False

    def check_prerequisite(self, course: Course, student: Student) -> bool:
        passedPrerequisiteCount = 0
        for prerequisiteCode in course.prerequisiteTo:
            for student_semester in student.transcript.semesters:
                for givenCourse in student_semester.given_courses:
                    if givenCourse.courseCode == prerequisiteCode:
                        passedPrerequisiteCount += 1

        if passedPrerequisiteCount == len(course.prerequisiteTo):
            student.enrolled_courses.append(course.name)
            student.student_semester.taken_credit = student.student_semester.taken_credit + course.credit
            course.enrolledStudents.append(student.id)
            return True
        else:
            student.logs.append(
                f"Cant add course: {course.name} to Student : {student.name} because of trying to take upper semester course")
            logging.info(
                f"Cant add course: {course.name} to Student : {student.name} because of trying to take upper semester course")
