import unittest
from Curriculum import Curriculum
from JsonParser import JsonParser
from Student import Student


class AdvisorTest(unittest.TestCase):
    def setUp(self):
        from Advisor import Advisor
        self.advisors = [Advisor("advisor")]
        self.student = Student("Sanji", "1501210017", 1)
        self.student.advisor = self.advisors[0]
        self.parser = JsonParser()
        self.curriculum = Curriculum()
        try:
            self.parser.parseCourses(self.curriculum, self.advisors)
        except IOError as e:
            e.printStackTrace()
        self.parameters = self.parser.parseParameters()
        self.parameters.max_course_per_semester = 10

    def testCourseEnrollWhenNoConflict(self):
        courses = [self.curriculum.get_course_by_code("CSE1241")]
        self.student.enroll(courses, self.curriculum, self.parameters)
        self.assertEqual(courses[0].name, self.student.enrolled_courses[0])

    def testCourseEnrollWhenTwoUpperSemester(self):
        courses = [self.curriculum.courses[2][0]]
        self.student.enroll(courses, self.curriculum, self.parameters)
        self.assertEqual(0, len(self.student.enrolled_courses))

    def testCourseEnrollWhenCollidingCourses(self):
        course1 = self.curriculum.get_course_by_code("CSE1241")
        course2 = self.curriculum.get_course_by_code("MATH1001")
        courses = [course1, course2]
        self.student.enroll(courses, self.curriculum, self.parameters)
        self.assertEqual(1, len(self.student.enrolled_courses))
        self.assertEqual(self.student.enrolled_courses[0], course1.name)

    def testCourseEnrollWhenMaxCourseLimitReached(self):
        self.parameters.max_course_per_semester = 1
        course1 = self.curriculum.get_course_by_code("CSE1241")
        course2 = self.curriculum.get_course_by_code("MBG1201")
        courses = [course1, course2]
        self.student.enroll(courses, self.curriculum, self.parameters)
        self.assertEqual(1, len(self.student.enrolled_courses))
        self.assertEqual(self.student.enrolled_courses[0], course1.name)

    def testCourseWhenPrerequisiteNotPassed(self):
        course1 = self.curriculum.get_course_by_code("CSE1241")
        course2 = self.curriculum.get_course_by_code("CSE1242")
        courses = [course1, course2]
        self.student.enroll(courses, self.curriculum, self.parameters)
        self.assertEqual(1, len(self.student.enrolled_courses))
        self.assertEqual(self.student.enrolled_courses[0], course1.name)
