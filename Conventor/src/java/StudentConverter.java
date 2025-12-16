import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

@FacesConverter("studentConverter")
public class StudentConverter implements Converter {

    private List<Student> loadStudents(FacesContext context) {
        List<Student> students = new ArrayList<>();
        ExternalContext ext = context.getExternalContext();

InputStream is = ext.getResourceAsStream("/WEB-INF/students.txt");
        System.out.println("Stream = " + is);

        if (is == null) {
            System.out.println("students.txt NOT FOUND");
            return students;
        }

        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(is, "UTF-8"))) {

            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;

                String[] parts = line.split(",");
                if (parts.length < 6) continue;

                String fn        = parts[0].trim();
                String firstName = parts[1].trim();
                String lastName  = parts[2].trim();
                int groupNo      = Integer.parseInt(parts[3].trim());
                int course       = Integer.parseInt(parts[4].trim());
                String email     = parts[5].trim();

                students.add(new Student(fn, firstName, lastName, groupNo, course, email));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Loaded students: " + students.size());
        return students;
    }

@Override
public Object getAsObject(FacesContext context, UIComponent component, String value)
        throws ConverterException {

    if (value == null || value.trim().isEmpty()) {
        return null;
    }

    String input = value.trim();
    System.out.println("INPUT = [" + input + "]");  // 👈

    List<Student> students = loadStudents(context);

    for (Student s : students) {
        String fullName = s.getFirstName() + " " + s.getLastName();
        System.out.println("CHECK: fn=" + s.getFn() +
                           ", email=" + s.getEmail() +
                           ", name=" + fullName);

        if (input.equalsIgnoreCase(s.getFn()))    return s;
        if (input.equalsIgnoreCase(s.getEmail())) return s;
        if (input.equalsIgnoreCase(fullName))     return s;
    }
    throw new ConverterException(
        new javax.faces.application.FacesMessage(
            javax.faces.application.FacesMessage.SEVERITY_ERROR,
            "Няма студент с такива данни: " + input,
            null
        )
    );
}

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value)
            throws ConverterException {
        if (value == null) return "";

        if (!(value instanceof Student)) {
            throw new ConverterException("Object not a Student: " + value);
        }
        return ((Student) value).getFn();
    }
}
