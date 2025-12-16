public class Student {
    private String fn;
    private String firstName;
    private String lastName;
    private int groupNo;
    private int course;
    private String email;

    public Student(String fn,String firstName, String lastName,
                   int groupNo, int course,String email){
        this.fn = fn;
        this.firstName = firstName;
        this.lastName = lastName;
        this.groupNo = groupNo;
        this.course = course;
        this.email = email;
    }

    public String getFn(){ return fn; }
    public String getFirstName(){ return firstName; }
    public String getLastName(){ return lastName; }
    public int getGroupNo(){ return groupNo; }
    public int getCourse(){ return course; }
    public String getEmail(){ return email; }

    @Override
    public String toString(){
        return fn + "-" + firstName + " " + lastName;
    }
}
