package jpabook.jpashop;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import jpabook.jpashop.test.MemberTest;

public class ValueMain {
    public static void main(String[] args){

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try{

            TypedQuery<MemberTest> query = em.createQuery("select m from MemberTest m",
                MemberTest.class);// 두번째 매개변수 = 엔티티 클래스명, 반화 클래스 명확, TypedQuery

            MemberTest result = query.getSingleResult();// 반환 0개, Exception(반환 1개 보장시 사용)
            // Spring Data Jpa : Optional, Null 반환, 예외x
            System.out.println("result = "+result);



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
