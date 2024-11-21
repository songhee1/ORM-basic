package jpabook.jpashop;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import java.util.List;
import jdk.swing.interop.SwingInterOpUtils;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.test.MemberTest;
import jpabook.jpashop.test.Team;
import org.hibernate.Hibernate;
import org.hibernate.jpa.internal.PersistenceUnitUtilImpl;

public class JpaMain {
    public static void main(String[] args){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try{
            Team team = new Team();
            team.setName("teamA");
            em.persist(team);

            Team team2 = new Team();
            team2.setName("teamB");
            em.persist(team2);

            MemberTest member1 = new MemberTest();
            member1.setName("member1");
            member1.setTeam(team);
            em.persist(member1);

            MemberTest member2 = new MemberTest();
            member2.setName("member2");
            member2.setTeam(team2);
            em.persist(member2);

            em.flush();
            em.clear();

            List<MemberTest> members = em.createQuery("select m from MemberTest m join fetch m.team", // LAZY loading + fetch join = Member, Team SELECT
                MemberTest.class).getResultList();

            for (MemberTest memberTest :members) {
                System.out.println(memberTest.getTeam().getName()); // Team SELECT X
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
