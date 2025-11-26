/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package student;

@ClassPreamble(
author="Valentin Kolev",
date="30.04.2001",
currentRevision = 1,
lastModified ="02.11.2025",
by="Valentin Kolev",
reviewers={"Ivan","Maria","Jordan"}
)

public final  class Student implements Comparable<Student>{

private String facultyNumber;
private String name;

public Student(String facultyNumber, String name)
{
    this.facultyNumber=facultyNumber;
    this.name=name;

}
public String getFacultyNumber()
{
    return facultyNumber;
}
public void setFacultyNumber(String facultyNumber)
{
    if(facultyNumber==null)
    {
        throw new IllegalArgumentException("EMPTY FN");
    }
    this.facultyNumber=facultyNumber;
}
public String getName()
{
    return name;
}
public void setName(String name)
{
   if(name==null)
   {
       throw new IllegalArgumentException("NO EMPTY NAME");
   }
   this.name=name;
}
@Override
public String toString()
{
    return "Student{"+"name=" + name +')';
}

    @Override
    public int compareTo(Student t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}

