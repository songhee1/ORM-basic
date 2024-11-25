package jpabook.jpashop;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import java.util.Collection;
import java.util.List;
import jpabook.jpashop.test.MemberTest;
import jpabook.jpashop.test.Team;

public class JpaMain {
    public static void main(String[] args){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try{
            Team team = new Team();
            em.persist(team);

            MemberTest member1 = new MemberTest();
            MemberTest member2 = new MemberTest();
            member1.setTeam(team);
            member2.setTeam(team);
            em.persist(member1);
            em.persist(member2);
            em.flush();
            em.clear();


            // 컬렉션 값 연관경로
            String query3 = "select m.username from Team t join t.members m"; // from절 명시적 내부조인(별칭), 탐색o
            Collection result3 = em.createQuery(query3, Collection.class).getResultList(); // 묵시적 내부조인, 탐색x
            for (Object o : result3) {
                System.out.println("o = "+ o);
            }

            tx.commit();
        }catch(Exception e){
            tx.rollback();
            e.printStackTrace();
        }finally {
            em.close();
        }

        emf.close();

    }
}
