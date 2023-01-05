from typing import List
from Course import Course
class Curriculum:
    def __init__(self):
        self.te_courses = []
        self.courses = [[] for _ in range(8)]

    @property
    def courses(self) -> List[List[Course]]:
        return self._courses

    @courses.setter
    def courses(self, courses: List[List[Course]]):
        self._courses = courses

    @property
    def te_courses(self) -> List[Course]:
        return self._te_courses

    @te_courses.setter
    def te_courses(self, te_courses: List[Course]):
        self._te_courses = te_courses

    def get_course(self, course_name: str) -> Course:
        try:
            for courses in self.courses:
                for c in courses:
                    if c.name == course_name:
                        return c
            for course in self.te_courses:
                if course.name == course_name:
                    return course
        except AttributeError:
            print("Error: course not found")
        return None

    def get_course_by_code(self, course_code: str) -> Course:
        for courses in self.courses:
            for c in courses:
                if c.code == course_code:
                    return c
        return None
