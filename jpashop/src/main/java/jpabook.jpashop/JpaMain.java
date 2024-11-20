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

            Member refMember = em.getReference(Member.class, member1.getId());
            Member findMember = em.find(Member.class, member1.getId());
            System.out.println("refMember = " + refMember.getClass()); // Proxy
            System.out.println("reference = " + findMember.getClass()); // Proxy

            System.out.println("m1 == reference" + (refMember == findMember)); // true 보장
            tx.commit();
        }catch(Exception e){
            tx.rollback();
        }finally {
            em.close();
        }

        emf.close();

    }
}
