import os
import json
import random
import jsonpickle
from typing import List
import logging
import sys

from Student import Student
from Section import Section
from TechnicalElective import TechnicalElective
from MandatoryCourse import MandatoryCourse
from SystemParameter import SystemParameter
from Department import Department
from Advisor import Advisor
from Instructor import Instructor
from Curriculum import Curriculum


class JsonParser:

    def __init__(self):
        logging.basicConfig(filename="logs.log",
                            filemode='a',
                            format='%(asctime)s,%(msecs)d %(name)s %(levelname)s %(message)s',
                            datefmt='%H:%M:%S',
                            level=logging.INFO,
                            )
        logging.getLogger().addHandler(logging.StreamHandler(sys.stdout))


    def parseCourses(self, curriculum: Curriculum, instructors: List[Instructor]) -> list:
        courseFiles = []
        courses = []
        for (dirpath, dirnames, filenames) in os.walk("courses"):
            for fileName in filenames:
                courseFiles.append(dirpath + "/" + fileName)
            break
        for i in range(len(courseFiles)):
            jsonFile = open(courseFiles[i])
            courseJson = json.load(jsonFile)
            for index, courseDict in enumerate(courseJson):
                logging.info(f"Parsed course {courseDict['name']}")
                if courseDict["type"] == "MD":
                    courses.append(self.createMandatoryCourse(courseDict))
                elif courseDict["type"] == "TE":
                    courses.append(self.createTechnicalElectiveCourse(courseDict))
        for i in range(len(courses)):
            randomInt = random.randint(0, len(instructors) - 1)
            courses[i].instructor = instructors[randomInt]
            logging.info(f"Added instructor {instructors[randomInt].name} to {courses[i].name}")
            instructors[randomInt].courses_offered_list.append(courses[i])
            if isinstance(courses[i], TechnicalElective) and courses[i].code != "TExxx":
                curriculum.te_courses.append(courses[i])
            else:
                semesterOfTheCourse = (courses[i].year - 1) * 2 + courses[i].term - 1
                curriculum.courses[semesterOfTheCourse].append(courses[i])

        return courses

    def createMandatoryCourse(self, courseDict) -> MandatoryCourse:
        courseSectionList = []
        labSectionList = []
        for index, sectionDict in enumerate(courseDict["course sessions"]):
            section = Section(sectionDict["day"], sectionDict["hour"])
            courseSectionList.append(section)

        for index, sectionDict in enumerate(courseDict["lab sessions"]):
            section = Section(sectionDict["day"], sectionDict["hour"])
            labSectionList.append(section)
        return MandatoryCourse(
            courseDict["name"],
            courseDict["code"],
            courseDict["term"],
            courseDict["year"],
            courseDict["credit"],
            courseDict["quota"],
            courseDict["prerequisiteTo"],
            courseSectionList,
            labSectionList
        )

    def createTechnicalElectiveCourse(self, courseDict) -> TechnicalElective:
        courseSectionList = []
        labSectionList = []
        for index, sectionDict in enumerate(courseDict["course sessions"]):
            section = Section(sectionDict["day"], sectionDict["hour"])
            courseSectionList.append(section)

        for index, sectionDict in enumerate(courseDict["lab sessions"]):
            section = Section(sectionDict["day"], sectionDict["hour"])
            labSectionList.append(section)
        return TechnicalElective(
            courseDict["name"],
            courseDict["code"],
            courseDict["term"],
            courseDict["year"],
            courseDict["credit"],
            courseDict["quota"],
            courseDict["prerequisiteTo"],
            courseSectionList,
            labSectionList
        )

    def parseParameters(self) -> SystemParameter:
        try:
            jsonFile = open("parameters.json")
        except FileNotFoundError:
            print("parameter json file not found")
        else:
            parametersJson = json.load(jsonFile)
            logging.info("Parsed parameters")
            return SystemParameter(parametersJson["semester"]
                                   , parametersJson["studentPerSemester"]
                                   , parametersJson["maxCoursePerSemester"]
                                   , parametersJson["maxCreditPerSemester"])

    def parseAdvisors(self, department: Department):
        try:
            advisorsFile = open("Advisors.json")
        except FileNotFoundError:
            print("Advisors.json file not found")
        else:
            advisorsList = json.load(advisorsFile)
            logging.info(f"Parsed advisors")
            for index, advisorDict in enumerate(advisorsList):
                department.get_advisor_list().append(Advisor(advisorDict["name"]))

    def outputStudentObjects(self,students:List[Student],filePath):
        jsonpickle.set_encoder_options('json', sort_keys=False, indent=4)
        try:
            for student in students:
                outputFile = open(filePath + "/" + student.id + ".json","w")
                jsonString = jsonpickle.encode(student,unpicklable=False)
                logging.info(f"Output student {student.name} as json")
                outputFile.write(jsonString)
        except FileNotFoundError:
            print(f"{filePath} not found")


