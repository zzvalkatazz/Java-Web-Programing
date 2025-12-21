/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;


import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.*;
import model.model.Hall;
import model.model.Reservation;
import model.model.User;

public class ReservationService {
private static EntityManagerFactory emf;

private static EntityManagerFactory getEmf() {
    if (emf == null) {
        emf = Persistence.createEntityManagerFactory("HallReservationSystemPU");
    }
    return emf;
}

public boolean requestReservation(User user, Hall hall, LocalDateTime start, LocalDateTime end, String eventType) {
    if (user == null || hall == null || start == null || end == null) return false;
    if (!end.isAfter(start)) return false;

    EntityManager em = getEmf().createEntityManager(); 
    try {
        TypedQuery<Long> q = em.createQuery(
            "SELECT COUNT(r) FROM Reservation r " +
            "WHERE r.hall.id = :hid AND r.status IN ('APPROVED','PENDING') " +
            "AND r.startTime < :end AND r.endTime > :start",
            Long.class
        );

        q.setParameter("hid", hall.getId());
        q.setParameter("start", start);
        q.setParameter("end", end);

        Long cnt = q.getSingleResult();
        if (cnt != null && cnt > 0) return false;

        em.getTransaction().begin();

        User managedUser = em.find(User.class, user.getId());
        Hall managedHall = em.find(Hall.class, hall.getId());
        if (managedUser == null || managedHall == null) {
            em.getTransaction().rollback();
            return false;
        }

        Reservation r = new Reservation();
        r.setUser(managedUser);
        r.setHall(managedHall);
        r.setStartTime(start);
        r.setEndTime(end);
        r.setEventType(eventType);
        r.setStatus("PENDING");
        r.setCreatedAt(LocalDateTime.now());

        em.persist(r);
        em.getTransaction().commit();
        return true;

    } catch (Exception ex) {
        if (em.getTransaction().isActive()) em.getTransaction().rollback();
        throw ex;
    } finally {
        em.close();
    }
}

public List<Reservation> findPending(){
    EntityManager em = getEmf().createEntityManager();
    try{
        return em.createQuery(  "SELECT r FROM Reservation r WHERE r.status = 'PENDING' ORDER BY r.createdAt DESC",
                    Reservation.class).getResultList();
    }finally{
        em.close();
    }
}
public void setStatus(Long reservationId, String status){
    EntityManager em =getEmf().createEntityManager();
    try{
          em.getTransaction().begin();
          Reservation r = em.find(Reservation.class, reservationId);
          if(r!=null){
              r.setStatus(status);
          }
           em.getTransaction().commit();
    }catch(Exception ex){
        if(em.getTransaction().isActive()) em.getTransaction().rollback();
        throw ex;
    }finally{
        em.close();
    }
}
public Reservation findLatestForUserAndHall(Long userId,Long hallId){
   if(userId == null || hallId == null) return null;
   
   EntityManager em = getEmf().createEntityManager();
   
   try{
       TypedQuery<Reservation> q = em.createQuery("SELECT r FROM Reservation r " +
            "WHERE r.user.id = :uid AND r.hall.id = :hid " +
            "ORDER BY r.createdAt DESC",
            Reservation.class);
       q.setParameter("uid",userId);
       q.setParameter("hid", hallId);
       q.setMaxResults(1);
       
       List<Reservation> list = q.getResultList();
       return list.isEmpty() ? null : list.get(0);
   }finally{
       em.close();
   }
}

}
