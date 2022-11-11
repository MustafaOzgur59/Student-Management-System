package iteration1;

import java.util.List;
import java.util.Objects;

public class Student {
    private String id;
    private String name;
    private Integer term;

    public Student(String id, String name, Integer term) {
        this.id = id;
        this.name = name;
        this.term = term;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getTerm() {
        return term;
    }

    public void setTerm(Integer term) {
        this.term = term;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return Objects.equals(id, student.id) && Objects.equals(name, student.name) && Objects.equals(term, student.term);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, term);
    }

    @Override
    public String toString() {
        return "Student{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", term=" + term +
                '}';
    }
}
