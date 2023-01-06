import unittest

from Curriculum import Curriculum
from Instructor import Instructor
from JsonParser import JsonParser
from MandatoryCourse import MandatoryCourse
from Section import Section
from Student import Student


class StudentTest(unittest.TestCase):
    def setUp(self) -> None:
        self.parser = JsonParser()
        self.curriculum = Curriculum()
        self.parameter = None
        self.instructors = [Instructor("David Guetta", "1233345")]
        self.student = None
        from Advisor import Advisor
        self.advisor = Advisor("advisor")
        self.course = MandatoryCourse("course1", "c1", 1, 1, 6, 100, [], [], [])
        self.course2 = MandatoryCourse("course2", "c2", 1, 1, 6, 100, [], [], [])
        self.courseToCollide = MandatoryCourse("course3", "c3", 1, 1, 6, 100, [], [], [])

        self.parser.parseCourses(self.curriculum, self.instructors)
        self.parameter = self.parser.parseParameters()
        self.student = Student("Sanji", "1501210017", 1)
        self.student.advisor = self.advisor
        self.course.courseSessions.append(Section("Wednesday", "13:00-13:50"))
        self.course2.prerequisiteTo.append("c1")
        self.courseToCollide.courseSessions.append(Section("Wednesday", "13:30-15:20"))

    def testStudentEnroll(self):
        courses = [self.course]
        self.student.enroll(courses, self.curriculum, self.parameter)
        self.assertIsNotNone(self.student.enrolled_courses, "Enrolled course array is null")
        self.assertEqual("course1", self.student.enrolled_courses[0])

    def testStudentGrade(self):
        courses = [self.course]
        self.student.enroll(courses, self.curriculum, self.parameter)
        self.instructors[0].grade_students(self.student, self.course)
        self.student.transcript.semesters.append(self.student.student_semester)
        assert self.student.transcript.semesters[0].given_courses[0].grade != -1
        assert self.student.transcript.semesters[0].given_courses[0].grade != 5
        assert self.student.transcript.semesters[0].given_courses[0].grade is not None

    def testCourseWithPrerquisite(self):
        courses = [self.course, self.course2]
        self.student.enroll(courses, self.curriculum, self.parameter)
        enrolled_courses = self.student.enrolled_courses
        expected_courses = ["course1"]
        self.assertListEqual(expected_courses, enrolled_courses)
