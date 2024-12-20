package jpabook.jpashop;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import java.util.List;
import jpabook.jpashop.test.MemberTest;
import jpabook.jpashop.test.MemberType;
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
            member.setAge(10);
            member.setType(MemberType.ADMIN);
            member.setTeam(team);
            member.setUsername("관리자1");
            em.persist(member);

            MemberTest member2 = new MemberTest();
            member.setAge(10);
            member.setType(MemberType.ADMIN);
            member.setTeam(team);
            member.setUsername("관리자2");
            em.persist(member2);

            em.flush();
            em.clear();


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
