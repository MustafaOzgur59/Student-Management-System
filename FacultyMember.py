from abc import ABC, abstractmethod
from iteration3 import Person

class FacultyMember(Person, ABC):
    def __init__(self, name: str, id: str):
        super().__init__(name, id)

    @abstractmethod
    def grade_students(self, student, course):
        pass

    def __str__(self) -> str:
        return f"FacultyMember {{}} {super().__str__()}"