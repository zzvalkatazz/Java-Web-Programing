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
import service.ReservationService;



@ManagedBean
@SessionScoped
public class AdminReservationBean implements Serializable{
   private final ReservationService reservationService = new ReservationService();
   
   @ManagedProperty(value ="#{loginBean}")
   private LoginBean loginBean;

  public List<Reservation> getPendingReservations(){   
      return reservationService.findPending();
  }
  
  public void approve(Reservation r){
      if(!isAdmin()) return;
      reservationService.setStatus(r.getId(),"APPROVED");
      msgInfo("Одобрено", "Заявката е одобрена.");
  }

  public void reject(Reservation r){
      if(!isAdmin()) return;
      reservationService.setStatus(r.getId(),"REJECTED");
      msgInfo("Отказано", "Заявката е отказана.");
  }
  private boolean isAdmin(){
      return loginBean !=null && loginBean.isAdmin();
  }
  
    private void msgInfo(String title,String text) {
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, title, text)); 
    }
     public LoginBean getLoginBean() { return loginBean; }
    public void setLoginBean(LoginBean loginBean) { this.loginBean = loginBean; }
}
