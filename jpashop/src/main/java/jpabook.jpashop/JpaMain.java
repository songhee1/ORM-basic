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
            em.persist(teamA);
            Team teamB = new Team();
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

            String query = "select m from MemberTest m"; // Team은 프록시 객체설정
            List<MemberTest> result = em.createQuery(query, MemberTest.class).getResultList(); //select member
            for (MemberTest member : result) { // teamA, teamB 각각 select
                System.out.println("member="+member.getUsername()+", team name = " + member.getTeam().getName());
                // 회원1, 팀A(SQL)
                // 회원2, 팀A(1차 캐시)
                // 회원3, 팀B(SQL)
                // 총 3번

                // 회원수 많을수록 N(SELECT TEAM)+1(SELECT MEMBER)쿼리, 즉시/지연로딩 마찬가지
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
