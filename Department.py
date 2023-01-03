from typing import List
from Student import Student
from Course import Course
from Advisor import Advisor
class Department:
    def __init__(self):
        self.student_list = []
        self.advisor_list = []

    def add_student(self, student: Student, course: Course) -> bool:
        if course.quota > len(course.enrolled_students):
            course.enrolled_students.add(student.id)
            return True
        else:
            return False

    def search_student(self, student: Student, course: Course) -> bool:
        return student.id in course.enrolled_students

    def remove_student(self, student: Student, course: Course):
        course.enrolled_students.remove(student.id)

    def get_student_list(self) -> List[Student]:
        return self.student_list

    def set_student_list(self, student_list: List[Student]):
        self.student_list = student_list

    def get_advisor_list(self) -> List[Advisor]:
        return self.advisor_list

    def set_advisor_list(self, advisor_list: List[Advisor]):
        self.advisor_list = advisor_list

    def get_student(self, student_id: str) -> Student:
        for student in self.student_list:
            if student.id == student_id:
                return student
        return None
