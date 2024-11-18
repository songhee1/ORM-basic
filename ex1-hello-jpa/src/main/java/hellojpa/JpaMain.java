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
            List<Member> result = em.createQuery("select m from Member as m", Member.class)
                .setFirstResult(1)
                .setMaxResults(8)
                .getResultList(); // 대상이 테이블이 아닌, 객체여야 함 -> Member entity(객체지향 쿼리)

            for(Member member : result){
                System.out.println("member.name : " + member.getName());
            }

            tx.commit();
        }catch(Exception e) {
            tx.rollback();
        }finally{
            em.close();
        }

        emf.close(); // WAS 내려갈때 EntityManagerFactory를 close 해주어야 함
    }
}
