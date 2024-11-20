package jpabook.jpashop;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import jdk.swing.interop.SwingInterOpUtils;
import jpabook.jpashop.domain.Member;

public class JpaMain {
    public static void main(String[] args){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try{
            Member member1 = new Member();
            member1.setName("member1");
            em.persist(member1);

            em.flush();
            em.clear();

            Member m1 = em.find(Member.class, member1.getId());
            Member reference = em.getReference(Member.class, member1.getId());
            System.out.println("m1 = " + m1.getClass());
            System.out.println("reference = " + reference.getClass());
            // 둘다 원본 반환 (프록시 객체 안나오는 이유 : 1. 성능상 이점X 2. JPA 메커니즘-트랜잭션 내 동시성 보장)

            System.out.println("m1 == reference" + (m1 == reference)); // true
            tx.commit();
        }catch(Exception e){
            tx.rollback();
        }finally {
            em.close();
        }

        emf.close();

    }
}
