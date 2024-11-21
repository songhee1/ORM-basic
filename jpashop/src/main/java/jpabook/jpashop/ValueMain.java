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

            //member.getAddress().setCity("xxx") 불가(객체 참조 공유 방지), 새 생성자로 변경
            Address newAddress = new Address("NewCity", address.getStreet(), address.getZipcode());
            member.setAddress(newAddress);

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
