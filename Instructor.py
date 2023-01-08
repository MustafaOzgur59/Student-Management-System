import random
from typing import List
import logging
from Course import Course
from GivenCourse import GivenCourse
from Person import Person


class Instructor(Person):
    def __init__(self, name: str, id: str):
        super().__init__(name, id)
        self.courses_offered_list = []

    def grade_students(self, student, course):
        try:
            int_random = max(random.randint(0, 8), min(random.randint(0, 8), random.randint(0, 8))) / 2
            # int_random = max(random.uniform(0, 4.5), min(random.uniform(0, 4.5), random.uniform(0, 4.5)))
            logging.info(f"Graded student {student.name} for course {course.name} grade is: {int_random}")
            student.student_semester.given_courses.append(GivenCourse(course.code, int_random, course.credit))
        except AttributeError:
            print("Error: student or course not found")