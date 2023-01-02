import random
from iteration3 import Course
from iteration3 import GivenCourse
from iteration3 import Student
from typing import List

class Instructor:
    def __init__(self, name: str, id: str):
        self.name = name
        self.id = id
        self.courses_offered_list = []

    def grade_students(self, student, course):
        int_random = max(random.uniform(0, 4.5), min(random.uniform(0, 4.5), random.uniform(0, 4.5)))
        print(f"Graded student {student.name} for course {course.name} grade is: {int_random}")
        student.student_semester.given_courses.append(GivenCourse(course.code, int_random, course.credit))

    @property
    def courses_offered_list(self) -> List[Course]:
        return self.courses_offered_list

    @courses_offered_list.setter
    def courses_offered_list(self, courses_offered_list: List[Course]):
        self.courses_offered_list = courses_offered_list
