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
            Team teamA = new Team();
            teamA.setName("teamA");
            em.persist(teamA);
            Team teamB = new Team();
            teamB.setName("teamB");
            em.persist(teamB);

            MemberTest member1 = new MemberTest();
            MemberTest member2 = new MemberTest();
            MemberTest member3 = new MemberTest();
            member1.setTeam(teamA);
            member1.setUsername("회원1");
            member2.setUsername("회원2");
            member3.setUsername("회원3");
            member2.setTeam(teamA);
            member3.setTeam(teamB);
            em.persist(member1);
            em.persist(member2);
            em.persist(member3);
            em.flush();
            em.clear();

            // 엔티티 직접 사용
            String query = "select m from MemberTest m where m.team = :team"; // 외래키값 TEAM_ID전달

            List<MemberTest> team = em.createQuery(query, MemberTest.class)
                .setParameter("team", teamA).getResultList();

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
