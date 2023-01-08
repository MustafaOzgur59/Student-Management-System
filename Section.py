from datetime import datetime
import logging

class Section:

    def __init__(self, day: str, hours: str):
        self.day = day
        self.hours = hours

    def checkSectionCollision(self, section1):
        courseHours = self.hours.split(",")  # 1 hour range 2 or 3
        stdCourseHours = section1.hours.split(",")  # 1 hour range  3 or 3
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
