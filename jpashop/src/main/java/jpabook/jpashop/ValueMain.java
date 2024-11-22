package jpabook.jpashop;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import java.util.List;
import jpabook.jpashop.test.Address;
import jpabook.jpashop.test.MemberTest;

public class ValueMain {
    public static void main(String[] args){

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try{
            List<MemberTest> result = em.createQuery(
                    "select m From MemberTest m where m.userName like '%kim%'", MemberTest.class) // 동적쿼리X
                .getResultList();// 엔티티 매핑 정보를 읽어 테이블 지정해 SQL작성

            for (MemberTest memberTest : result) {
                System.out.println("member = " + memberTest);
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
