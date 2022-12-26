from JsonParser import JsonParser

jsonParser = JsonParser()
courses = jsonParser.parseCourses()
for i in range(len(courses)):
    print(courses[i])
    print(f"Type is {type(courses[i])} ")