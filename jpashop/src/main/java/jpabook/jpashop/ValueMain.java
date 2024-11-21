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
            Address address = new Address("city", "street", "zipcode");

            MemberTest member = new MemberTest();
            member.setName("member1");
            member.setAddress(address);
            em.persist(member);

            Address copyAddress = new Address(address.getCity(), address.getStreet(),
                address.getZipcode()); // [2-1의 대안]값 타입은 복사해서 사용

            MemberTest member2 = new MemberTest();
            member2.setName("member2");
//            member2.setAddress(address); // [1]값 타입 공유
            member2.setAddress(copyAddress);
            em.persist(member2);

            member.getHomeAddress().setCity("new City"); //[1] 값 타입 공유 : UPDATE 2회, [2] UPDATE 1회, 공유X

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
