import random

from Student import Student
from StudentSemester import StudentSemester
from StudentSemester import StudentSemester
from Curriculum import Curriculum
from JsonParser import JsonParser
from Department import Department
from SystemParameter import SystemParameter


class RegistrationSystem:

    def __init__(self):
        self.jsonParser = JsonParser()
        self.curriculum = Curriculum()
        self.department = Department()
        self.systemParameter = self.jsonParser.parseParameters()

    def readJsonFiles(self):
        self.jsonParser.parseAdvisors(self.department)
        self.jsonParser.parseCourses(self.curriculum, self.department.get_advisor_list())

    def beginSimulation(self):
        self.initializeStudents()
        self.prepareInitializedStudents()
        print("Simulation starting")
        self.jsonParser.outputStudentObjects(self.department.get_student_list(), "./students/inputStudents")
        self.enrollStudents()
        self.gradeStudents()
        self.calculateTranscript()
        self.jsonParser.outputStudentObjects(self.department.get_student_list(), "C:/Users/Mustafa/Desktop/iteson/CSE3063F22P1_GRP4/students/outputStudents")
        print("Simulation ending")

    def enrollStudents(self):
        for student in self.department.student_list:
            availableCourses = self.getAvailableCourses(student)
            student.enroll(availableCourses, self.curriculum, self.systemParameter)

    def calculateTranscript(self):
        for student in self.department.get_student_list():
            student.transcript.get_semesters().append(student.student_semester)
            for std_semester in student.transcript.get_semesters():
                std_semester.calculate_yano()
                std_semester.letter_grades = []
                std_semester.calculate_letter_grade()
            student.transcript.calculate_gpa()

    def gradeStudents(self):
        for advisor in self.department.get_advisor_list():
            for course in advisor.courses_offered_list:
                for studentId in course.enrolledStudents:
                    advisor.grade_students(self.department.get_student(studentId), course)

    def getAvailableCourses(self, student: Student):
        courses = self.curriculum.courses
        availableCourses = []
        for courseList in courses:
            for course in courseList:
                if student.transcript.contains_course(course.code):
                    if student.transcript.get_max_grade(course.code) < 2:
                        availableCourses.append(course)
                else:
                    availableCourses.append(course)

        return availableCourses

    def initializeStudents(self):
        for i in range(1, 5):
            term = 2 * (i - 1) + self.systemParameter.semester
            departmentCode = "1501"
            entryYear = str(22 - i)
            for j in range(1, self.systemParameter.student_per_semester + 1):
                entryPlace = ""
                if j < 10:
                    entryPlace = "00" + str(j)
                else:
                    entryPlace = "0" + str(j)
                studentNumber = departmentCode + entryYear + entryPlace
                student = Student(studentNumber, studentNumber, term)
                advisor = self.department.get_advisor_list()[
                    random.randint(0, len(self.department.get_advisor_list()) - 1)]
                student.advisor = advisor
                self.department.student_list.append(student)

    def prepareInitializedStudents(self):
        for i in range(1, 5):
            currentStudentTerm = 2 * (i - 1) + self.systemParameter.semester
            for k in range(1, currentStudentTerm):
                for j in range(0, self.systemParameter.student_per_semester):
                    student = self.department.get_student_list()[
                        (i - 1) * self.systemParameter.student_per_semester + j]
                    student.term = k
                    student.student_semester = StudentSemester(k)
                    student.enrolled_courses = []
                    availableCourses = self.getAvailableCourses(student)
                    student.enroll(availableCourses, self.curriculum, self.systemParameter)

                    self.gradeStudents()
                    student.student_semester.calculate_letter_grade()
                    student.student_semester.calculate_yano()
                    student.transcript.get_semesters().append(student.student_semester)
                    student.transcript.calculate_gpa()
                    student.term = currentStudentTerm

                    # resetting curriculum
                    self.curriculum = Curriculum()
                    self.department.advisor_list = []
                    self.jsonParser.parseAdvisors(self.department)
                    self.jsonParser.parseCourses(self.curriculum, self.department.get_advisor_list())

            self.curriculum = Curriculum()
            self.jsonParser.parseCourses(self.curriculum, self.department.get_advisor_list())

        for student in self.department.get_student_list():
            student.student_semester = StudentSemester(student.term)
            student.enrolled_courses = []
            student.logs = []

        self.curriculum = Curriculum()
        self.department.advisor_list = []
        self.jsonParser.parseAdvisors(self.department)
