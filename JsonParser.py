import os
import json

from Section import Section
from TechnicalElective import TechnicalElective
from MandatoryCourse import MandatoryCourse


class JsonParser:

    def __init__(self):
        pass

    def parseCourses(self) -> list:
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
                if courseDict["type"] == "MD":
                    courses.append(self.createMandatoryCourse(courseDict))
                elif courseDict["type"] == "TE":
                    courses.append(self.createTechnicalElectiveCourse(courseDict))
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
