    /*
     * To change this license header, choose License Headers in Project Properties.
     * To change this template file, choose Tools | Templates
     * and open the template in the editor.
     */
    package service;

    import java.security.SecureRandom;
    import java.util.List;
    import model.model.User;
    import javax.persistence.*;
    public class UserService {
        private static  EntityManagerFactory emf;
        private static final SecureRandom RNG = new SecureRandom();

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
           public boolean existsEmail(String email){
            EntityManager em = getEmf().createEntityManager();
                try {
                Long cnt = em.createQuery(
                    "SELECT COUNT(u) FROM User u WHERE u.email = :ue", Long.class)
                    .setParameter("ue", email)
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
                if (existsUsername(username) || existsEmail(email)) {
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
       private String generateTempPassword(int len){
           String chars = "ABCDEFGHJKLMNPQRSTUVWXYZabcdefghijkmnopqrstuvwxyz23456789!@#";
           StringBuilder sb = new StringBuilder();
           for(int i=0; i<len;i++){
               sb.append(chars.charAt(RNG.nextInt(chars.length())));
           }
           return sb.toString();
       }
    public String resetPassword(Long userId){
     EntityManager em = getEmf().createEntityManager();
    try{
     em.getTransaction().begin();

     User u = em.find(User.class, userId);
     if(u == null){
         em.getTransaction().rollback();
         return null;
     }

     String temp = generateTempPassword(10);
     u.setPassword(temp);

     em.merge(u);
     em.getTransaction().commit();
     return temp;
    }catch(Exception ex){
         if(em.getTransaction().isActive()) em.getTransaction().rollback();
            throw ex;
    }finally{ 
    em.close();
    }
    }
    public boolean changePassword(Long userId, String oldPass, String newPass){
        EntityManager em = getEmf().createEntityManager();
        try{
            em.getTransaction().begin();

            User u = em.find(User.class,userId);
            if(u == null){
                em.getTransaction().rollback();
                return false;
            }
            if(u.getPassword()== null || !u.getPassword().equals(oldPass)){
                em.getTransaction().rollback();
                return false;
            }
            u.setPassword(newPass);

            em.merge(u);
            em.getTransaction().commit();
            return true;
        }catch(Exception ex){
            if(em.getTransaction().isActive()) em.getTransaction().rollback();
            throw ex;
        }finally{
            em.close();
        }
    }
    public List<User> findAllUsers(){
        EntityManager em = getEmf().createEntityManager();
        try{
          return em.createQuery("SELECT u FROM User u ORDER BY u.id", User.class)
                     .getResultList();  
        }finally{
            em.close();
        }
    }
    }
