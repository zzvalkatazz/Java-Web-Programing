package service;

import model.model.Hall;

import javax.persistence.*;
import java.util.List;

public class HallService {

    private static EntityManagerFactory emf;

    private static EntityManagerFactory getEmf() {
        if (emf == null) {
            emf = Persistence.createEntityManagerFactory("HallReservationSystemPU");
        }
        return emf;
    }

    public List<Hall> findAll() {
        EntityManager em = getEmf().createEntityManager();
        try {
            return em.createQuery("SELECT h FROM Hall h ORDER BY h.location, h.name", Hall.class)
                     .getResultList();
        } finally {
            em.close();
        }
    }

    public void create(Hall hall) {
        EntityManager em = getEmf().createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(hall);
            tx.commit();
        } finally {
            if (tx.isActive()) tx.rollback();
            em.close();
        }
    }

    public Hall update(Hall hall) {
        EntityManager em = getEmf().createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Hall merged = em.merge(hall);
            tx.commit();
            return merged;
        } finally {
            if (tx.isActive()) tx.rollback();
            em.close();
        }
    }

    public void delete(Long id) {
        EntityManager em = getEmf().createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Hall h = em.find(Hall.class, id);
            if (h != null) em.remove(h);
            tx.commit();
        } finally {
            if (tx.isActive()) tx.rollback();
            em.close();
        }
    }

    public long count() {
        EntityManager em = getEmf().createEntityManager();
        try {
            return em.createQuery("SELECT COUNT(h) FROM Hall h", Long.class).getSingleResult();
        } finally {
            em.close();
        }
    }

    public void seedIfEmpty() {
        if (count() > 0) return;

        String location = "NDK (пример)";

        create(hall("Зала 1 (Голяма)", location, 3000, "AUDITORIUM"));
        create(hall("Зала 2 (Разширяема)", location, 1000, "FLEXIBLE"));
        create(hall("Тържествена зала", location, 700, "CEREMONY"));

        for (int i = 1; i <= 5; i++) {
            create(hall("Конферентна зала " + i, location, 250, "CONFERENCE"));
        }

        for (int i = 1; i <= 10; i++) {
            create(hall("Танцова зала " + i, location, null, "DANCE"));
        }
    }

    private Hall hall(String name, String location, Integer capacity, String type) {
        Hall h = new Hall();
        h.setName(name);
        h.setLocation(location);
        h.setCapacity(capacity);
        h.setType(type);
        return h;
    }
    public Hall findById(Long id){
     EntityManager em = getEmf().createEntityManager();
     try{
         return em.find(Hall.class,id);
     }finally{
         em.close();
     }
    }
}
