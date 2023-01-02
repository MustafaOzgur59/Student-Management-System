class SystemParameter:
    def __init__(self, semester, student_per_semester, max_course_per_semester, max_credit_per_semester):
        self.semester = semester
        self.student_per_semester = student_per_semester
        self.max_course_per_semester = max_course_per_semester
        self.max_credit_per_semester = max_credit_per_semester

    @classmethod
    def from_json(cls, data):
        return cls(**data)

    def get_semester(self):
        return self.semester

    def set_semester(self, semester):
        self.semester = semester

    def get_student_per_semester(self):
        return self.student_per_semester

    def set_student_per_semester(self, student_per_semester):
        self.student_per_semester = student_per_semester

    def get_max_course_per_semester(self):
        return self.max_course_per_semester

    def set_max_course_per_semester(self, max_course_per_semester):
        self.max_course_per_semester = max_course_per_semester

    def get_max_credit_per_semester(self):
        return self.max_credit_per_semester

    def set_max_credit_per_semester(self, max_credit_per_semester):
        self.max_credit_per_semester = max_credit_per_semester

    def __repr__(self):
        return f"SystemParameter(semester={self.semester}, student_per_semester={self.student_per_semester}, max_course_per_semester={self.max_course_per_semester}, max_credit_per_semester={self.max_credit_per_semester})"
