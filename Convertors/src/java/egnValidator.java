

import java.time.LocalDate;
import java.time.Period;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author vk300
 */
@FacesValidator("egnValidator")
public class egnValidator implements Validator{
    
    private static final int[] WEIGHTS ={2,4,8,5,10,9,7,3,6};
    
    @Override
    public void validate(FacesContext context,UIComponent component, Object value) throws ValidatorException{
        
      if(value == null){
          throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,"Въведете ЕГН",null )); 
      }  
      String egn = value.toString().trim();
      if(!egn.matches("\\d{10}")){
           throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,"EГН трябва да е 10 цифри",null ));
      }
      if(!checkControlDigit(egn)){
           throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,"невалидно контролно число",null ));
      }
        LocalDate birthDate = parseBirthDate(egn);
        if (birthDate == null) {
            throw new ValidatorException(
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Невалидна дата в ЕГН.", null));
        }

        if (!checkRegion(egn)) {
            throw new ValidatorException(
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Невалиден регион (област) в ЕГН.", null));
        }
        boolean isFemale =((egn.charAt(8)-'0') %2 ==1);
        
        int age = Period.between(birthDate, LocalDate.now()).getYears();
        
        String ageStatus;
        if(age<16){
            ageStatus="малолетен";
        }else if(age<18){
            ageStatus ="непълнолетен";
        }else{
            ageStatus="пълнотелен";
        }
        component.getAttributes().put("birthDate", birthDate);
        component.getAttributes().put("isFemale", isFemale);
        component.getAttributes().put("age", age);
        component.getAttributes().put("ageStatus", ageStatus);
    }   
       private boolean checkControlDigit(String egn){
       int sum=0;
       for(int i=0;i<9;i++){
           int digit = egn.charAt(i)-'0';
           sum+=digit*WEIGHTS[i];
       }
       int remainder = sum%11;
       int control = (remainder<10) ? remainder :0;
       return control == (egn.charAt(9)-'0');
          
    }
    private LocalDate parseBirthDate(String egn){
        int year = Integer.parseInt(egn.substring(0,2));
        int month = Integer.parseInt(egn.substring(2,4));
        int day = Integer.parseInt(egn.substring(4,6));
        int realYear;
        int realMonth;
        
        if(month>=1 && month<=12){
            realYear = 1900+ year;
            realMonth = month;
        }else if(month>=21 && month<=32){
            realYear= 1800+year;
            realMonth = month-20;
        }else if(month>=41 && month<=52){
            realYear = 2000+year;
            realMonth = month-40;
        }else{
            return null;
        }
        try {
    return LocalDate.of(realYear, realMonth, day);
} catch (RuntimeException e) {
    return null;
}
    }       
    private boolean checkRegion(String egn){
        int region = Integer.parseInt(egn.substring(6,9));
        if(region<0 || region > 999){
            return false;
        }
        
        if(region>925){
            return false;
        }
        return true;
    }
    
    }

