package iteration2;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

public class StudentSerializer extends StdSerializer<Student> {

    protected StudentSerializer(){
        super(Student.class);
    }
    protected StudentSerializer(Class<Student> t) {
        super(t);
    }

    protected StudentSerializer(JavaType type) {
        super(type);
    }

    protected StudentSerializer(Class<?> t, boolean dummy) {
        super(t, dummy);
    }

    protected StudentSerializer(StdSerializer<?> src) {
        super(src);
    }

    @Override
    public void serialize(Student student, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField("id",student.getId());
        jsonGenerator.writeStringField("name",student.getName());
        jsonGenerator.writeNumberField("term",student.getTerm());
        jsonGenerator.writeObjectField("transcript",student.getTranscript());
        jsonGenerator.writeFieldName("enrolledCourses");
        jsonGenerator.writeObject(student.getEnrolledCourses().toArray(new String[0]));
        jsonGenerator.writeFieldName("logs");
        jsonGenerator.writeObject(student.getLogs().toArray(new String[0]));
        jsonGenerator.writeObjectField("studentSemester",student.getStudentSemester());
        jsonGenerator.writeFieldName("advisor");
        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField("name",student.getAdvisor().getName());
        jsonGenerator.writeStringField("id",student.getAdvisor().getId());
        jsonGenerator.writeEndObject();
        jsonGenerator.writeEndObject();
    }
}
