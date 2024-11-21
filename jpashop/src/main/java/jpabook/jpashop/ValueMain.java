package jpabook.jpashop;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import jakarta.persistence.criteria.CriteriaBuilder.In;
import jpabook.jpashop.test.Address;
import jpabook.jpashop.test.MemberTest;
import jpabook.jpashop.test.Period;

public class ValueMain {
    public static void main(String[] args){

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try{
            MemberTest member = new MemberTest();
            member.setName("hello");
            member.setAddress(new Address("city", "street", "zipcode"));
            member.setWorkPeriod(new Period());
            em.persist(member);

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
