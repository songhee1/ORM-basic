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
            Member member = new Member();
            Member member2 = new Member();
            Member member3 = new Member();
            System.out.println("===구분선===");
            em.persist(member); // 시퀀스ID값 생성-1, 시퀀스ID값 생성-51
            em.persist(member2);
            em.persist(member3);
            System.out.println("===구분선===");

            System.out.println("member.id = "+member.getId());  // 1,51  |1
            System.out.println("member2.id = "+member2.getId());// MEMORY|2
            System.out.println("member3.id = "+member3.getId());// MEMORY|3

            tx.commit(); // INSERT 쿼리
        }catch(Exception e) {
            tx.rollback();
        }finally{
            em.close();
        }

        emf.close(); // WAS 내려갈때 EntityManagerFactory를 close 해주어야 함
    }
}
