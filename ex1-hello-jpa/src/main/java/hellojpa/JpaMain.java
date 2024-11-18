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
            //1차 캐시 존재x, SELECT 쿼리
            Member member1 = em.find(Member.class, 101L);
            //1차 캐시 존재O
            Member member2 = em.find(Member.class, 101L);

            // 같은 트랜잭션 안에서 객체 비교시 true
            System.out.println("result = "+(member1 == member2));
            tx.commit(); // INSERT 쿼리 발생O
        }catch(Exception e) {
            tx.rollback();
        }finally{
            em.close();
        }

        emf.close(); // WAS 내려갈때 EntityManagerFactory를 close 해주어야 함
    }
}
