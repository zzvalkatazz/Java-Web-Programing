                /*
                 * To change this license header, choose License Headers in Project Properties.
                 * To change this template file, choose Tools | Templates
                 * and open the template in the editor.
                 */
                package beans;

                import java.io.Serializable;
                import java.util.List;
                import javax.faces.application.FacesMessage;
                import javax.faces.bean.ManagedBean;
                import javax.faces.bean.ManagedProperty;
                import javax.faces.bean.SessionScoped;
                import javax.faces.context.FacesContext;
                import model.model.Reservation;
                import model.model.User;
                import service.ReservationService;


                @ManagedBean
                @SessionScoped
                public class UserReservationBean implements Serializable{

                    private final ReservationService reservationService = new ReservationService();
                    @ManagedProperty(value="#{loginBean}")
                    private LoginBean loginBean;

                    private List<Reservation> myReservations;

                    public void load(){
                        User u = loginBean.getLoggedUser();
                        if(u == null){
                            myReservations = java.util.Collections.emptyList();
                            return;
                        }
                        myReservations = reservationService.findForUser(u.getId());
                        System.out.println("myReservations size=" + myReservations.size());
               if (loginBean.getLoggedUser() != null) {
               System.out.println("uid=" + loginBean.getLoggedUser().getId());
                    }
                    }
                    
                    public void cancel(Reservation r){
                        User u =(loginBean != null) ? loginBean.getLoggedUser():null;
                        if(u == null || r == null){
                           msg("Грешка", "Не сте влезли.", FacesMessage.SEVERITY_ERROR);
                           return;
                        }
                        
                        boolean ok = reservationService.cancelReservation(r.getId(),u.getId());

                        if(ok){
                            msg("Успех", "Резервацията е отменена.", FacesMessage.SEVERITY_INFO);
                            load();
                        }else{
                            msg("Грешка", "Не може да се отмени (вече е обработена).", FacesMessage.SEVERITY_ERROR);
                        } 
                      }
                    
                    public boolean canCancel(Reservation r){
                        if(r == null || r.getStatus() == null) return false;
                        return "PENDING".equals(r.getStatus()) || "APPROVED".equals(r.getStatus());
                    }
                    
                      private void msg(String title, String text, FacesMessage.Severity sev) {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(sev, title, text));
                    }
                      
                    public List<Reservation> getMyReservations(){return myReservations;}
                    public LoginBean getLoginBean(){ return loginBean;}
                    public void setLoginBean(LoginBean loginBean) {this.loginBean = loginBean;}
                }

