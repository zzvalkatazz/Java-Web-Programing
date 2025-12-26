package beans;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import model.model.Hall;
import model.model.User;
import service.HallService;
import service.ReservationService;


@ManagedBean
@SessionScoped
public class ReservationBean implements Serializable {

    private Long hallId;
    private Date startDate;
    private Date endDate;
    private String eventType;

    private final ReservationService reservationService = new ReservationService();
    private final HallService hallService = new HallService();

    @ManagedProperty(value = "#{loginBean}")
    private LoginBean loginBean;

    public String requestReservation() {
        if (loginBean == null || loginBean.getLoggedUser() == null) {
            msgError("Трябва да сте влязъл в системата");
            return null;
        }
        if (hallId == null) {
            msgError("Избери зала.");
            return null;
        }
        if (startDate == null || endDate == null) {
            msgError("Избери начална и крайна дата/час.");
            return null;
        }

        LocalDateTime start = LocalDateTime.ofInstant(startDate.toInstant(), ZoneId.systemDefault());
        LocalDateTime end = LocalDateTime.ofInstant(endDate.toInstant(), ZoneId.systemDefault());

        if (!end.isAfter(start)) {
            msgError("Крайният час трябва да е след началния.");
            return null;
        }

        User u = loginBean.getLoggedUser();
        Hall h = hallService.findById(hallId);
        if (h == null) {
            msgError("Залата не е намерена.");
            return null;
        }

        boolean ok = reservationService.requestReservation(u, h, start, end, eventType);
        if (!ok) {
            msgError("Има конфликт с друга резервация или заявка.");
            return null;
        }

        msgInfo("Успех", "Заявката е изпратена към администратор.");
        clearForm();
        return "/halls.xhtml?faces-redirect=true";
    }

    private void clearForm() {
        startDate = null;
        endDate = null;
        eventType = null;
        hallId = null;
    }

    private void msgError(String text) {
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Грешка", text));
    }

    private void msgInfo(String title, String text) {
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, title, text));
    }

    // getters/setters
    public Long getHallId() { return hallId; }
    public void setHallId(Long hallId) { this.hallId = hallId; }

    public Date getStartDate() { return startDate; }
    public void setStartDate(Date startDate) { this.startDate = startDate; }

    public Date getEndDate() { return endDate; }
    public void setEndDate(Date endDate) { this.endDate = endDate; }

    public String getEventType() { return eventType; }
    public void setEventType(String eventType) { this.eventType = eventType; }

    public LoginBean getLoginBean() { return loginBean; }
    public void setLoginBean(LoginBean loginBean) { this.loginBean = loginBean; }

    public List<Hall> getHalls() {
        return hallService.findAll();
    }
}
