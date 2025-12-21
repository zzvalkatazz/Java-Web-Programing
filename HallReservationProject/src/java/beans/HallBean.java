package beans;

import model.model.Hall;
import service.HallService;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.io.Serializable;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import model.model.Reservation;
import model.model.User;
import service.ReservationService;

@ManagedBean(name="hallBean")
@SessionScoped
public class HallBean implements Serializable {

    private final HallService hallService = new HallService();
   private final ReservationService reservationService = new ReservationService();
    private List<Hall> halls;
    private Hall selected;

    private boolean editMode;
    @ManagedProperty(value="#{loginBean}")
    private LoginBean loginBean;
@PostConstruct
public void init() {
     if (selected == null) {
        selected = new Hall();
    }
    
     halls = hallService.findAll();
}

    public void reload() {
        halls = hallService.findAll();
    }

    public void newHall() {
        selected = new Hall();
         editMode = false;
        selected.setLocation("НДК");
        selected.setType("CONFERENCE");
    }
public void prepareEdit(Hall h) {
    this.selected = h;
    this.editMode = true;
}
    public void save() {
        if ("DANCE".equals(selected.getType())) {
            selected.setCapacity(null);
        }
        if (!editMode) {
            hallService.create(selected);
        } else {
            hallService.update(selected);
        }
        reload();
    }

public void delete(Hall h) {
    try {
        hallService.delete(h.getId());  
        refresh();                     
        FacesContext.getCurrentInstance().addMessage(null,
            new FacesMessage(FacesMessage.SEVERITY_INFO, "OK", "Залата е изтрита."));
    } catch (Exception e) {
        FacesContext.getCurrentInstance().addMessage(null,
            new FacesMessage(FacesMessage.SEVERITY_ERROR, "Грешка", "Не може да се изтрие залата."));
    }
}
public void refresh() {
  
     halls = hallService.findAll();

    if (selected == null) {
        selected = new Hall();
    }
}



    public List<Hall> getHalls() { return halls; }

    public Hall getSelected() { return selected; }
    public void setSelected(Hall selected) { this.selected = selected; }
    
public void setLoginBean(LoginBean loginBean){
    this.loginBean = loginBean;
}

public String userReservationInfo(Hall h){
    if(h == null || h.getId() == null) return "-";
    if(loginBean == null) return "-";
    
    User u = loginBean.getLoggedUser();
    if(u == null) return "-";
    
    Reservation r = reservationService.findLatestForUserAndHall(u.getId(),h.getId());
    if(r==null) return "-";
    
    String status = r.getStatus();
    DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

 String start = (r.getStartTime()!=null)   ? r.getStartTime().format(fmt): "?";
 String end = (r.getEndTime()!=null)   ? r.getEndTime().format(fmt): "?";
 return status + "(" + start +"-" +end +")";
}
 
 
}
