import os
import json
from TechnicalElective import  TechnicalElective
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
                    course = self.createTechnicalElectiveCourse(courseDict)
                    courses.append(self.createTechnicalElectiveCourse(courseDict))
        return courses

    def createMandatoryCourse(self, courseDict) -> MandatoryCourse:
        return MandatoryCourse(
            courseDict["name"],
            courseDict["code"],
            courseDict["term"],
            courseDict["year"],
            courseDict["credit"],
            courseDict["quota"],
            courseDict["prerequisiteTo"],
            courseDict["course sessions"],
            courseDict["lab sessions"],
        )

    def createTechnicalElectiveCourse(self, courseDict) -> TechnicalElective:
        return TechnicalElective(
            courseDict["name"],
            courseDict["code"],
            courseDict["term"],
            courseDict["year"],
            courseDict["credit"],
            courseDict["quota"],
            courseDict["prerequisiteTo"],
            courseDict["course sessions"],
            courseDict["lab sessions"],
        )
