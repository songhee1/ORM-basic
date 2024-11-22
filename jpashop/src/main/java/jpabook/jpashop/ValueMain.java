package jpabook.jpashop;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import java.util.List;
import jpabook.jpashop.test.Address;
import jpabook.jpashop.test.MemberTest;
import jpabook.jpashop.test.Team;

public class ValueMain {
    public static void main(String[] args){

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try{

            MemberTest member = new MemberTest();
            member.setUsername("member1");
            member.setAge(10);
            em.persist(member);

            em.flush();
            em.clear();

            //엔티티 프로젝션
            List<MemberTest> result = em.createQuery("select m from MemberTest m", // m : 엔티티, 엔티티 반환
                MemberTest.class).getResultList(); // SELECT, 영속성 컨텍스트에서 관리

            result.get(0).setAge(20); // UPDATE

            //엔티티 프로젝션
            List<Team> result2 = em.createQuery("select m.team from MemberTest m", Team.class) // 쿼리간단하나, join SELECT
                .getResultList(); // 쿼리 예측 x(권장x)
            List<Team> result3 = em.createQuery("select t from MemberTest m join m.team t", Team.class) // join SELECT
                .getResultList(); // 쿼리 예측, 조인은 명시적으로 하는 것 권장

            // 임베디드 타입 프로젝션
            List<Address> result4 = em.createQuery("select o.address from OrderTest o",
                    Address.class)
                .getResultList();

            // 스칼라 타입 프로젝션
            em.createQuery("select distinct m.username, m.age from MemberTest m") // join SELECT
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
