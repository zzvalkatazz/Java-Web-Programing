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
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;

@ManagedBean
@SessionScoped
public class LoginBean implements Serializable{
    private String username;
    private String password;
    
    private User loggedUser;
    
    private final UserService userService = new UserService();
      public String login() {
        User u = userService.login(username, password);
        if (u == null) {
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Грешен вход", "Невалидно потребителско име или парола."));
            return null; // остава на login.xhtml
        }
        
        loggedUser = u;
        password = null;
        
        return "/halls.xhtml?faces-redirect=true";
}
 public String logout(){
     FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "/login.xhtml?faces-redirect=true"; 
 }
 
 public boolean isLoggedIn(){
     return loggedUser != null;
 }
 public boolean isAdmin(){
     return loggedUser != null && "ADMIN".equalsIgnoreCase(loggedUser.getRole());
 }
  public boolean isUser(){
     return loggedUser != null && "USER".equalsIgnoreCase(loggedUser.getRole());
 }
  public boolean isCanReserve(){
    return loggedUser != null && ("USER".equals(loggedUser.getRole()) || "ADMIN".equals(loggedUser.getRole()));
}
 public String getUsername(){
     return username;
 }
 public void setUsername(String username) { 
       this.username = username; 
   }
 public String getPassword(){
     return password;
 }
 public void setPassword(String password) { 
     this.password = password; 
 }
 public User getLoggedUser() { 
     return loggedUser; 
 }
private String adminEmail;
public String getAdminEmail() {
    if (adminEmail == null || adminEmail.trim().isEmpty()) {
        adminEmail = new UserService().getAdminEmail(); 
    }
    return adminEmail;
}
}