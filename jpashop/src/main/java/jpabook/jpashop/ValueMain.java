package jpabook.jpashop;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import java.util.List;
import jpabook.jpashop.test.MemberTest;

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

            TypedQuery<MemberTest> query = em.createQuery("select m from MemberTest m where m.username = :username",
                MemberTest.class);
            query.setParameter("username", "member1"); // 파라미터 바인딩-위치기반x
            MemberTest result = query.getSingleResult();

            //메소드 체이닝
            /*MemberTest chainingResult = em.createQuery(
                "select m from MemberTest m where m.username = :username",
                MemberTest.class).setParameter("username", "member1").getSingleResult();*/

            System.out.println("singleResult = "+result.getUsername());

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
