    /*
     * To change this license header, choose License Headers in Project Properties.
     * To change this template file, choose Tools | Templates
     * and open the template in the editor.
     */
    package beans;

    import java.io.Serializable;
    import javax.faces.application.FacesMessage;
    import javax.faces.bean.ManagedBean;
    import javax.faces.bean.ManagedProperty;
    import javax.faces.bean.ViewScoped;
    import javax.faces.context.FacesContext;

    import model.model.User;
    import service.UserService;

   
    @ManagedBean(name="changePasswordBean")
    @ViewScoped
    public class ChangePasswordBean implements Serializable{
        @ManagedProperty(value ="#{loginBean}")
        private LoginBean loginBean;

        private final UserService userService = new UserService();
        private String oldPass;
        private String newPass;
        private String confirmPass;
        private String generatedForUser;
        public String change(){
            User u = loginBean.getLoggedUser();
            if(u == null){
              msg("Няма логнат потребител.");
              return null;
        }
            if(newPass == null || newPass.length()<4){
                msg("твърде кратка парола");
              return null;
            }
            if(!newPass.equals(confirmPass)){
                msg("паролите не съвпадат");
                return null;
            }
            boolean ok = userService.changePassword(u.getId(), oldPass, newPass);
            if(!ok){
                msg("Старата парола е грешна");
                return null;
            }
             
            generatedForUser = u.getUsername();
            FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
             FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(
                    FacesMessage.SEVERITY_INFO,
                    "сменена е паролата на ",generatedForUser)
                            
                
            );
             
            oldPass = newPass =confirmPass = null;
           return "/halls.xhtml?faces-redirect=true";
        }
        private void msg(String text){
             FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Грешка", text));
        }
        public void setLoginBean(LoginBean loginBean){
            this.loginBean = loginBean;
        }
        public String getOldPass(){
            return oldPass;
        }
        public void setOldPass(String oldPass){
            this.oldPass = oldPass;
        }
        public String getNewPass(){
            return newPass;
        }
        public void setNewPass(String newPass){
            this.newPass = newPass;
        }
        public String getConfirmPass(){
            return confirmPass;
        }
        public void setConfirmPass(String confirmPass){
            this.confirmPass = confirmPass;
        }
    }

