class GivenCourse:

    def __init__(self, courseCode, grade, credit):
        self.courseCode = courseCode
        self.grade = grade
        self.credit = credit
        self.letterGrade = ""

    def getLetterGrade(self):
        return self.letterGrade

    def setLetterGrade(self, letterGrade):
        self.letterGrade = letterGrade

    def calculate_letter_grade(self):
        if self.grade == 0:
            self.letterGrade = "FF"
        elif self.grade == 0.5:
            self.letterGrade = "FD"
        elif self.grade == 1:
            self.letterGrade = "DD"
        elif self.grade == 1.5:
            self.letterGrade = "DC"
        elif self.grade == 2:
            self.letterGrade = "CC"
        elif self.grade == 2.5:
            self.letterGrade = "CB"
        elif self.grade == 3:
            self.letterGrade = "BB"
        elif self.grade == 3.5:
            self.letterGrade = "BA"
        else:
            self.letterGrade = "AA"
