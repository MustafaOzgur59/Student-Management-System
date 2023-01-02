class Transcript:
    def __init__(self):
        self.gpa = None
        self.semesters = []
        self.cumulative_credit = 0

    def calculate_gpa(self):
        self.calculate_cumulative_credit()
        grade = 0
        for i in range(len(self.semesters)):
            grade += self.semesters[i].get_yano() * self.semesters[i].get_completed_credit()
        self.gpa = (int((grade / self.cumulative_credit) * 100)) / 100.0

    def calculate_cumulative_credit(self):
        self.cumulative_credit = 0
        for i in range(len(self.semesters)):
            self.cumulative_credit += self.semesters[i].get_completed_credit()

    def get_gpa(self):
        return self.gpa

    def set_gpa(self, gpa):
        self.gpa = gpa

    def get_semesters(self):
        return self.semesters

    def set_semesters(self, semesters):
        self.semesters = semesters

    def contains_course(self, course_code):
        for semester in self.semesters:
            for course in semester.get_given_courses():
                if course.get_course_code() == course_code:
                    return True
        return False

    def get_given_course(self, course_code):
        for semester in self.semesters:
            for course in semester.get_given_courses():
                if course.get_course_code() == course_code:
                    return course
        return None

    def get_max_grade(self, course_code):
        max_grade = 0
        for semester in self.semesters:
            for course in semester.get_given_courses():
                if course.get_course_code() == course_code and course.get_grade() > max_grade:
                    max_grade = course.get_grade()
        return max_grade

    def __repr__(self):
        return f"Transcript(gpa={self.gpa}, semesters={self.semesters}, cumulative_credit={self.cumulative_credit})"
