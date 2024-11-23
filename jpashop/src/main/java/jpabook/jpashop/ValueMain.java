package jpabook.jpashop;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import java.util.List;
import jpabook.jpashop.test.Address;
import jpabook.jpashop.test.MemberDTO;
import jpabook.jpashop.test.MemberTest;
import jpabook.jpashop.test.Team;

public class ValueMain {
    public static void main(String[] args){

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try{
            Team team = new Team();
            team.setName("teamA");
            em.persist(team);
            MemberTest member = new MemberTest();
            member.setUsername("memberA");
            member.setAge(10);

            member.setTeam(team);
            em.persist(member);

            em.flush();
            em.clear();

            String query = "select m from MemberTest m left join m.team t on t.name = 'teamA'"; //on t.id=m.team_id and t.name = 'teamA', 조인조건문에 추가
            String query2 = "select m from MemberTest m left join Team t on t.name = m.username"; //on t.name = m.username, 조인조건문에 추가

            List<MemberTest> result = em.createQuery(
                    query, MemberTest.class)
                .getResultList();

            List<MemberTest> result2 = em.createQuery(
                    query2, MemberTest.class)
                .getResultList();

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
