    /*
     * To change this license header, choose License Headers in Project Properties.
     * To change this template file, choose Tools | Templates
     * and open the template in the editor.
     */
    package beans;

    import java.io.Serializable;
    import java.util.ArrayList;
    import java.util.List;
    import javax.faces.application.FacesMessage;
    import javax.faces.bean.ManagedBean;
    import javax.faces.bean.ManagedProperty;
    import javax.faces.bean.ViewScoped;
    import javax.faces.context.FacesContext;
    import model.model.User;
    import service.UserService;

    @ManagedBean(name = "adminUsersBean")
    @ViewScoped
    public class AdminUserBean implements Serializable{

        @ManagedProperty(value ="#{loginBean}")
        private LoginBean loginBean;

        private final UserService userService = new UserService();

        private List<User> users;
        private String generatedPassword;
        private String generatedForUser;
        private String globalFilter;

        public void load(){
            if(loginBean == null || !loginBean.isAdmin()){
                users = new ArrayList<>();
                return;
            }
            users = userService.findAllUsers();
        }

        public void resetPasswordFor(User u){
            if( u == null || u.getId() == null) return;

            String temp = userService.resetPassword(u.getId());
            if(temp == null){
                FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Грешка", "Потребителят не е намерен."));
                return; 
            }
            generatedPassword = temp;
            generatedForUser = u.getUsername();

             FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(
                    FacesMessage.SEVERITY_INFO,
                    "Нова временна парола за " + generatedForUser,
                    temp
                )
            );

             load();
        }
        public List<User> getUsers(){return users;}
        public String getGeneratedPassword() {return generatedPassword;}
        public String getGeneratedForUser(){return generatedForUser;}
        public String getGlobalFilter(){
            return globalFilter;
        }
        public void setLoginBean(LoginBean loginBean) { this.loginBean = loginBean; }

        public void setGlobalFilter(String globalFilter) {
        this.globalFilter = globalFilter;
    }


    }