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

            // 단일값 연관경로
            String query = "select m.team.name from MemberTest m"; // name은 상태필드, 경로탐색의 끝, 묵시적 내부조인(탐색o)
            String query2 = "select m.team from MemberTest m"; // select team from member join teawm, 조인(묵시적내부조인)

            List<String> result = em.createQuery(query, String.class).getResultList();
            List<Team> result2 = em.createQuery(query2, Team.class).getResultList();

            // 컬렉션 값 연관경로
            String query3 = "select t.members from Team t";
            String query4 = "select size(t.members) from Team t"; // size 사용가능
            Collection result3 = em.createQuery(query3, Collection.class).getResultList(); // 묵시적 내부조인, 탐색x
            for (Object o : result3) {
                System.out.println("o = "+ o);
            }
            Integer result4 = em.createQuery(query4, Integer.class).getSingleResult();
            System.out.println(result4);

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
