import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

@Named
@SessionScoped
public class StudentBean implements Serializable {
    private Student student;

    public Student getStudent() {
        return student;
    }
    public void setStudent(Student student) {
        this.student = student;
    }

    public String search() {
        // нищо не правим – student вече е попълнен от конвертора
        return null;
    }
}
