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

            //컬렉션 페치조인시 페이징API X
            String query = "select t from Team t"; // LAZY LOADING

            List<Team> result = em.createQuery(query, Team.class) // SELECT TEAM
                .setFirstResult(0)
                .setMaxResults(2)
                .getResultList();

            for (Team team : result) {
                System.out.println("team = "+team.getName()+", team.getMembers().size() = "+ team.getMemberList().size()); // 중복제거(같은 식별자 가진 team 엔티티 제거)
                for (MemberTest member : team.getMemberList()){
                    System.out.println("member = " +member);// TEAM A -> SELECT MEMBER, TEAM B -> SELECT MEMBER
                }
                System.out.println();
            }
            // 일반적으로 지연로딩 사용시 총 3번 쿼리, N+1문제
            // join fetch 는 일대다 관계에서 사용불가

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
