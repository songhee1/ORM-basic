package hellojpa;

import jakarta.persistence.*;
import java.util.List;

public class JpaMain {

    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        //EntityManager : 고객 요청이 올때마다 생성해 사용 - 공유x
        EntityManager em = emf.createEntityManager();
        //code
        EntityTransaction tx = em.getTransaction();
        tx.begin(); // db 트랜잭션 시작 - 데이터의 모든 변경은 트랜잭션 안에서 일어나야 함
        try{
            //비영속
             Member member = new Member();
             member.setId(100L);
             member.setName("HelloJPA");

             //영속상태, db에 저장x
            System.out.println("BEFORE");
            em.persist(member); //  INSERT 쿼리 발생X
            System.out.println("AFTER");

            tx.commit(); // INSERT 쿼리 발생O
        }catch(Exception e) {
            tx.rollback();
        }finally{
            em.close();
        }

        emf.close(); // WAS 내려갈때 EntityManagerFactory를 close 해주어야 함
    }
}
