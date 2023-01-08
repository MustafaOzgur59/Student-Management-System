from abc import abstractmethod

from Person import Person


class FacultyMember(Person):
    def __init__(self, name: str, id: str):
        super().__init__(name, id)

    @abstractmethod
    def grade_students(self, student, course):
        pass

    def __str__(self) -> str:
        return f"FacultyMember {{}} {super().__str__()}"