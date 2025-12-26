
package beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import model.model.Reservation;
import service.ReservationService;


@ManagedBean(name ="adminReservationHistoryBean")
@ViewScoped
public class AdminReservationHistoryBean implements Serializable{
    @ManagedProperty(value="#{loginBean}")
    private LoginBean loginBean;
    private Reservation selected;
    private final ReservationService reservationService = new ReservationService();
    
    private List<Reservation> all;
    public void load(){
        if(loginBean == null || !loginBean.isAdmin()){
            all = new ArrayList<>();
            return;
        }
        all = reservationService.findAll();
    }
    
        public void approve(Reservation r){
         if(r == null || r.getId() == null) return;
         boolean ok = reservationService.approveReservation(r.getId());
         if(!ok){
           msg("Не може да се одобри", "Има конфликт с друга одобрена резервация.",FacesMessage.SEVERITY_ERROR);
        } else { 
            msg("Одобрено", "Резервацията е одобрена.",FacesMessage.SEVERITY_INFO); 
         }
         load();
        }
      public void reject(Reservation r){
          if(r == null || r.getId() == null) return;
            reservationService.setStatus(r.getId(),"REJECTED");
          msg("Отказано", "Статусът е REJECTED.",FacesMessage.SEVERITY_WARN);
          load();
      }  
      public void cancel(Reservation r){
          reservationService.setStatus(r.getId(),"CANCELED");
          msg("Отменено", "Статусът е CANCELED.",FacesMessage.SEVERITY_WARN);
          load();
      }
      
     private void msg(String title, String text, FacesMessage.Severity sev) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(sev, title, text));
     }
     public List<Reservation> getAll(){
         return all;
     }
   public void setLoginBean(LoginBean loginBean) { this.loginBean = loginBean; }
   public void openDetails(Reservation r){
       this.selected =r;
   }
   public Reservation getSelected(){
       return selected;
   }
   
   public String statusClass(String status){
       if(status == null) return "";
       switch (status){
           case "APPROVED" : return "st-ok";
           case "PENDING"  : return "st-pend";
           case "REJECTED" : return "st-rej";
           case "CANCELED" : return "st-can";
           default: return "";
       }
   }
}
