import random
from typing import List

from Course import Course
from GivenCourse import GivenCourse


class Instructor:
    def __init__(self, name: str, id: str):
        self.name = name
        self.id = id
        self.courses_offered_list = []

    def grade_students(self, student, course):
        int_random = max(random.uniform(0, 4.5), min(random.uniform(0, 4.5), random.uniform(0, 4.5)))
        print(f"Graded student {student.name} for course {course.name} grade is: {int_random}")
        student.student_semester.given_courses.append(GivenCourse(course.code, int_random, course.credit))
