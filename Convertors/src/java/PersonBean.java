
import java.io.Serializable;
import java.time.LocalDate;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author vk300
 */
@Named
@SessionScoped
public class PersonBean implements Serializable{
    private String egn;
    private String greetings;
    
    private int age;
    private boolean female;
    private LocalDate birthDate;
    
public String getEgn(){
    return egn;
}
public void setEgn(String egn){
    this.egn=egn;
}

public String getGreeting(){
    return greetings;
}
public int getAge(){
    return age;
}
public boolean isFemale(){
    return female;
}
public LocalDate getBithDate(){
    return birthDate;
}

public String processEgn(){
        if (female) {
            if (age <= 16) {
                greetings = "Здравейте малка мис";
            } else if (age < 18) { // 17 г.
                greetings = "Здравейте млада г-це";
            } else if (age == 18) {
                greetings = "Здравейте г-це";
            } else {
                greetings = "Здравейте, г-жо";
            }
        } else { // Мъж
            if (age < 18) {
                greetings = "Здравей, младежо";
            } else {
                greetings = "Здравейте, г-н";
            }
        }
        return null; 
}
    public void updateFromComponent(javax.faces.component.UIComponent comp) {
        Object a = comp.getAttributes().get("age");
        Object f = comp.getAttributes().get("isFemale");
        Object bd = comp.getAttributes().get("birthDate");

        if (a != null) age = (Integer) a;
        if (f != null) female = (Boolean) f;
        if (bd != null) birthDate = (LocalDate) bd;
    }
}
