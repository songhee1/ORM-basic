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
            member1.setUsername("회원1");
            member2.setUsername("회원2");
            member3.setUsername("회원3");

            member1.setTeam(teamA);
            member2.setTeam(teamA);
            member3.setTeam(teamB);

            em.persist(member1);
            em.persist(member2);
            em.persist(member3);

            //모든 회원 나이를 10으로 UPDATE
            //flush 자동 호출(commit, query)
            int resultCountColumn = em.createQuery("update MemberTest  m set m.age = 20")
                .executeUpdate(); // db에만 반영

            // 영속성 컨텍스트에는 반영 아직x
            System.out.println("member1.getAget() = "+member1.getAge());// 0
            System.out.println("member2.getAget() = "+member2.getAge());// 0
            System.out.println("member3.getAget() = "+member3.getAge());// 0

            MemberTest findMember = em.find(MemberTest.class, member1.getId());
            System.out.println(findMember.getAge()); // 0

            em.clear(); // 영속성 컨텍스트 초기화
            MemberTest findMember2 = em.find(MemberTest.class, member1.getId());
            System.out.println(findMember2.getAge()); // 20


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
