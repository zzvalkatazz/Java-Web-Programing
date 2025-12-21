/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import java.util.List;
import model.model.User;
import javax.persistence.*;
public class UserService {
    private static  EntityManagerFactory emf;
     private static EntityManagerFactory getEmf() {
        if (emf == null) {
            emf = Persistence.createEntityManagerFactory("HallReservationSystemPU");
        }
        return emf;
    }
    public User login(String username,String password){
               EntityManager em = getEmf().createEntityManager();
        try {
            TypedQuery<User> q = em.createQuery(
                "SELECT u FROM User u WHERE u.username = :un AND u.password = :pw",
                User.class
            );
            q.setParameter("un", username);
            q.setParameter("pw", password);

            List<User> result = q.getResultList();
            return result.isEmpty() ? null :result.get(0);
            
        } finally {
            em.close();
        }
    }
    public boolean existsUsername(String username){
        EntityManager em = getEmf().createEntityManager();
            try {
            Long cnt = em.createQuery(
                "SELECT COUNT(u) FROM User u WHERE u.username = :un", Long.class)
                .setParameter("un", username)
                .getSingleResult();
            return cnt != null && cnt > 0;
        } finally {
            em.close();
        }
    }
    public User register(String username, String password,String fullName, String email){
        EntityManager em = getEmf().createEntityManager();
        EntityTransaction tx = em.getTransaction();
        
        try{
            if (existsUsername(username)) {
                return null;
        }
            tx.begin();
            User u =new User();
            u.setUsername(username);
            u.setPassword(password);
            u.setFullName(fullName);
            u.setEmail(email);
            u.setRole("USER");
            
            em.persist(u);
            tx.commit();
            
            return u;
            
    }catch(Exception e){
        if(tx.isActive()) tx.rollback();
        throw e;
    }finally{
            em.close();
        }
}
    public String getAdminEmail(){
        EntityManager em = getEmf().createEntityManager();
        try{
            return (String) em.createNativeQuery(
            "SELECT email FROM users WHERE role = 'ADMIN' LIMIT 1"
        ).getResultList().stream().findFirst().orElse(null);
        }catch(Exception e){
            return null;
        }finally{
            em.close();
        }
    }
    
}
