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

            String query = "select m from MemberTest m join fetch m.team"; // Team 엔티티 초기화, 페치조인>지연로딩, 다대일 조인
            String query2 = "select t from Team t join fetch t.members"; // 일대다 조인, data 뻥튀기(단점)
            List<MemberTest> result = em.createQuery(query, MemberTest.class).getResultList(); //select member, team
            List<Team> result2 = em.createQuery(query2, Team.class).getResultList();
            for (MemberTest member : result) { // teamA, teamB 각각 select
                System.out.println("member="+member.getUsername()+", team name = " + member.getTeam().getName());
                // 회원1, 팀A(1차 캐시)
                // 회원2, 팀A(1차 캐시)
                // 회원3, 팀B(1차 캐시)
                // 총 1번
            }

            for (Team team : result2) {
                System.out.println("team = "+team.getName()+", team.getMembers().size() = "+ team.getMemberList().size()); // 중복발생
                for (MemberTest member : team.getMemberList()){
                    System.out.println("member = " +member);
                }
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
