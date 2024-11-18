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

            //영속
            Member member = em.find(Member.class, 150L);//1차 캐시의 스냅샷으로 저장
            member.setName("ZZZZZ"); //DATA 변경-1차 캐시의 value값이 변경

            System.out.println("===구분선===");
            
            tx.commit(); // 커밋 시점에 UPDATE 쿼리 발생
        }catch(Exception e) {
            tx.rollback();
        }finally{
            em.close();
        }

        emf.close(); // WAS 내려갈때 EntityManagerFactory를 close 해주어야 함
    }
}
