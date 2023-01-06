import unittest

from Curriculum import Curriculum
from Instructor import Instructor
from JsonParser import JsonParser


class CourseTest(unittest.TestCase):
    def setUp(self):
        self.parser = JsonParser()
        self.curriculum = Curriculum()
        self.instructors = [Instructor("instructor", "123214")]
        try:
            self.parser.parseCourses(self.curriculum, self.instructors)
        except IOError as e:
            e.printStackTrace()

    def testCourseCollision(self):
        course1 = self.curriculum.get_course_by_code("CSE1241")
        course2 = self.curriculum.get_course_by_code("MATH1001")
        # checks course collision function with 2 colliding courses
        self.assertTrue(course1.checkCollision(course2))