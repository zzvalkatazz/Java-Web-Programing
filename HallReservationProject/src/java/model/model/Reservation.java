package model.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.Basic;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * @author vk300
 */
@Entity
public class Reservation implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
     
    @Convert(converter = LocalDateTimeConverter.class)
    @Basic
    private LocalDateTime startTime;

    @Convert(converter = LocalDateTimeConverter.class)
    @Basic
    private LocalDateTime endTime;

    @Basic
    private String eventType;

    @Basic
    private String status;
     
    @Convert(converter = LocalDateTimeConverter.class)
    @Basic
    private LocalDateTime createdAt;

    
      @ManyToOne(optional = false)
    @JoinColumn(name = "user_id")   
    private User user;

    // ✅ добави това
    @ManyToOne(optional = false)
    @JoinColumn(name = "hall_id")   
    private Hall hall;
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
  public User getUser() {
      return user; 
  }
    public void setUser(User user) {
        this.user = user; 
    }

    public Hall getHall() {
        return hall; 
    }
    public void setHall(Hall hall) {
        this.hall = hall; 
    }
}