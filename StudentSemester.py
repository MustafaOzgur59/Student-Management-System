class StudentSemester:
    def __init__(self, semester_no:int):
        self.semester_no = semester_no
        self.yano = None
        self.taken_credit = 0
        self.completed_credit = 0
        self.note = 0
        self.given_courses = []
        self.letter_grades = []

    def calculate_yano(self):
        self.calculate_cumulative_credit()
        self.note = 0
        for course in self.given_courses:
            self.note += course.credit * course.grade
        if self.completed_credit == 0:
            self.yano = 0
        else:
            self.yano = int((self.note / self.completed_credit) * 100) / 100.0

    def calculate_cumulative_credit(self):
        self.completed_credit = 0
        for course in self.given_courses:
            self.completed_credit += course.credit

    def calculate_letter_grade(self):
        for course in self.given_courses:
            course.calculate_letter_grade()
            if course.grade == 0:
                self.letter_grades.append("FF")
            elif course.grade == 0.5:
                self.letter_grades.append("FD")
            elif course.grade == 1:
                self.letter_grades.append("DD")
            elif course.grade == 1.5:
                self.letter_grades.append("DC")
            elif course.grade == 2:
                self.letter_grades.append("CC")
            elif course.grade == 2.5:
                self.letter_grades.append("CB")
            elif course.grade == 3:
                self.letter_grades.append("BB")
            elif course.grade == 3.5:
                self.letter_grades.append("BA")
            elif course.grade == 4:
                self.letter_grades.append("AA")

@property
def yano(self):
    return self.yano

@yano.setter
def yano(self, yano):
    self.yano = yano


@property
def taken_credit(self):
    return self.taken_credit


@taken_credit.setter
def taken_credit(self, taken_credit):
    self.taken_credit = taken_credit


@property
def completed_credit(self):
    return self.completed_credit


@completed_credit.setter
def completed_credit(self, completed_credit):
    self.completed_credit = completed_credit


@property
def note(self):
    return self.note


@note.setter
def note(self, note):
    self.note = note


@property
def semester_no(self):
    return self.semester_no


@semester_no.setter
def semester_no(self, semester_no):
    self.semester_no = semester_no


@property
def given_courses(self):
    return self.given_courses


@given_courses.setter
def given_courses(self, given_courses):
    self.given_courses = given_courses


@property
def letter_grades(self):
    return self.letter_grades


@letter_grades.setter
def letter_grades(self, letter_grades):
    self.letter_grades = letter_grades


def __str__(self):
    return "StudentSemester{" + \
           "semesterNo=" + str(self.semester_no) + \
           ", yano=" + str(self.yano) + \
           ", takenCredit=" + str(self.taken_credit) + \
           ", completedCredit=" + str(self.completed_credit) + \
           ", note=" + str(self.note) + \
           ", givenCourses=" + str(self.given_courses) + \
           ", letterGrades=" + str(self.letter_grades) + \
           '}'
