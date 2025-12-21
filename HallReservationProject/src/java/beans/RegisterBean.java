/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import model.model.User;
import service.UserService;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

@ManagedBean
@RequestScoped
public class RegisterBean {
    private String username;
    private String password;
    private String confirmPassword;
    private String fullName;
    private String email;
    private final UserService userService = new UserService();
    public String register(){
        if(username == null || username.trim().isEmpty()){
            msgError("Въведи потребителско име.");
            return null;
        }
         if(password == null || password.length()<4){
            msgError("Паролата трябва да е поне 4 символа.");
            return null;
        }
           if (fullName == null || fullName.trim().isEmpty()) {
            msgError("Въведи пълно име.");
            return null;
        }

        if (email == null || !email.matches(".+@.+\\..+")) {
            msgError("Невалиден email адрес.");
            return null;
        }
          if(!password.equals(confirmPassword)){
            msgError("Паролите не съвпадат.");
            return null;
        }
          
       User u = userService.register(username.trim(), password, fullName.trim(), email.trim());
          if(u == null){
              msgError("Това име вече е заето");
              return null;
          }
           FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, "Успех", "Регистрацията е успешна. Влез с профила си."));

        return "/login.xhtml?faces-redirect=true";
    }
    private void msgError(String text){
         FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Грешка", text));
    }
    
    public String getUsername(){
        return username;
    }
    public void setUsername(String username){
        this.username=username;
    }
     public String getPassword(){
        return password;
    }
    public void setPassword(String password){
        this.password=password;
    } 
         public String getConfirmPassword(){
        return confirmPassword;
    }
    public void setConfirmPassword(String confirmPassword){
        this.confirmPassword=confirmPassword;
    } 
        public String getFullName() {
            return fullName; 
        }
    public void setFullName(String fullName) {
        this.fullName = fullName; 
    }

    public String getEmail() {
        return email; 
    }
    public void setEmail(String email) {
        this.email = email; 
    }
}
