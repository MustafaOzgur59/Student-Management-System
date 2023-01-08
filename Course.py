
class Course:

    def __init__(self, name: str, code: str, term: int, year: int, credit: int, quota: int, prerequisiteTo,
                 courseSessions, labSessions):
        self.name = name
        self.code = code
        self.term = term
        self.year = year
        self.credit = credit
        self.quota = quota
        self.prerequisiteTo = prerequisiteTo
        self.courseSessions = courseSessions
        self.labSessions = labSessions
        self.instructor = ""
        self.enrolledStudents = []

    def __str__(self) -> str:
        return f"\nname : {self.name} \n " \
               f"code : {self.code} \n " \
               f"term : {self.term} \n " \
               f"year : {self.year} \n " \
               f"credit : {self.credit} \n" \
               f"quota : {self.quota} \n" \
               f"prerequisiteTo : {self.prerequisiteTo} \n" \
               f"courseSessions : {self.courseSessions} \n" \
               f"labSessions : {self.labSessions} \n"

    def checkCollision(self, studentCourse):
        for courseSection in self.courseSessions:
            for stdCourseSection in studentCourse.courseSessions:
                # if the two sections are in the same day
                if stdCourseSection.day == courseSection.day:
                    return courseSection.checkSectionCollision(stdCourseSection)
        return False

    def addStudent(self, student, curriculum, system_parameters, advisor):
        pass
