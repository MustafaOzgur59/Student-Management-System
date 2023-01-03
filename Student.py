
from typing import List
from random import Random
import Curriculum
import Course
import SystemParameter
import StudentSemester
import Transcript
import Advisor
from Person import Person


class Student(Person):
    def __init__(self, id: str, name: str, term: int):
        super().__init__(id, name)
        self.term = term
        self.enrolled_courses = []
        self.student_semester = StudentSemester(term)
        self.transcript = Transcript()
        self.advisor = None
        self.logs = []

    @property
    def term(self) -> int:
        return self._term

    @term.setter
    def term(self, term: int):
        self._term = term

    @property
    def enrolled_courses(self) -> List[str]:
        return self._enrolled_courses

    @enrolled_courses.setter
    def enrolled_courses(self, enrolled_courses: List[str]):
        self._enrolled_courses = enrolled_courses

    @property
    def student_semester(self) -> StudentSemester:
        return self._student_semester

    @student_semester.setter
    def student_semester(self, student_semester: StudentSemester):
        self._student_semester = student_semester

    @property
    def transcript(self) -> Transcript:
        return self._transcript

    @transcript.setter
    def transcript(self, transcript: Transcript):
        self._transcript = transcript

    @property
    def advisor(self) -> Advisor:
        return self._advisor

    @advisor.setter
    def advisor(self, advisor: Advisor):
        self._advisor = advisor

    @property
    def logs(self) -> List[str]:
        return self._logs

    @logs.setter
    def logs(self, logs: List[str]):
        self._logs = logs

    def __eq__(self, other: object) -> bool:
        if not isinstance(other, Student):
            return False
        return (self.term == other.term and
                self.enrolled_courses == other.enrolled_courses and
                self.student_semester == other.student_semester and
                self.transcript == other.transcript and
                self.advisor == other.advisor and
                self.logs == other.logs)

    def __hash__(self):
        return hash((self.term, self.enrolled_courses, self.student_semester,
                     self.transcript, self.advisor, self.logs))

    def __str__(self):
        return (f"Student{{term={self.term}, enrolled_courses={self.enrolled_courses}, "
                f"student_semester={self.student_semester}, transcript={self.transcript}, "
                f"advisor={self.advisor}, logs={self.logs}}} {super().__str__()}")

    def enroll(self, available_courses: List[Course], curriculum: Curriculum, system_parameters: SystemParameter):
        for available_course in available_courses:
            if available_course.code == "TExxx":
                random = Random()
                course = curriculum.TE_COURSES[random.randint(0, len(curriculum.TE_COURSES))]
                self.advisor.enroll_student(course, self, curriculum, system_parameters)
            else:
                self.advisor.enroll_student(available_course, self, curriculum, system_parameters)
