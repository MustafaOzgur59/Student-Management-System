from datetime import datetime
import logging


class Course:

    def __init__(self, name:str, code:str, term:int, year:int, credit:int, quota:int, prerequisiteTo, courseSessions, labSessions):
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
                    courseHours = courseSection.hours.split(",")  # 1 hour range 2 or 3
                    stdCourseHours = stdCourseSection.hours.split(",")  # 1 hour range  3 or 3
                    for courseHour in courseHours:
                        splitHours = courseHour.split("-")  # 2 hours
                        for studentCourseHour in stdCourseHours:
                            studentSplitHours = studentCourseHour.split("-")  # 2 hour
                            try:
                                startDate = datetime.strptime(splitHours[0], '%H:%M')
                                endDate = datetime.strptime(splitHours[1], '%H:%M')
                                stdStartDate = datetime.strptime(studentSplitHours[0], '%H:%M')
                                stdEndDate = datetime.strptime(studentSplitHours[1], '%H:%M')
                                if (startDate < stdStartDate < endDate) or (
                                        stdStartDate < startDate < stdEndDate):
                                    return True
                            except ValueError as exception:
                                logging.error("Invalid date format" + str(exception))
        return False

    def addStudent(self, student, curriculum, system_parameters, advisor):
        pass

