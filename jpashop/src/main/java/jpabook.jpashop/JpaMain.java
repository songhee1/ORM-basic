package jpabook.jpashop;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
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
            Member member2 = new Member();
            member2.setName("member2");
            Member member3 = new Member();
            member3.setName("member3");

            em.persist(member1);
            em.persist(member2);
            em.persist(member3);
            em.flush(); //INSERT 발생
            em.clear();

            //1. 프록시 초기화
//            Member findMember = em.find(Member.class, member.getId()); //SELECT 발생
            Member findMember = em.getReference(Member.class, member1.getId()); //SELECT 발생x, 프록시 객체 반환

            System.out.println("BEFORE findMember = "+findMember.getClass()); // ~Proxy 출력, target = null
            System.out.println("findMember.id = " + findMember.getId());
            System.out.println("findMember.username = " + findMember.getName()); // 초기화요청O, SELECT 발생
            System.out.println("findMember.username = " + findMember.getName()); // 초기화요청X
            System.out.println("AFTER findMember = "+findMember.getClass()); // ~Proxy 출력, target = 원본엔티티

            //2. 타입 체크
            Member m1 = em.find(Member.class, member2.getId());
//            Member m2 = em.find(Member.class, member3.getId());
            Member m3 = em.getReference(Member.class, member3.getId());
//            System.out.println("m1 == m2 : " + (m1.getClass() == m2.getClass())); // true
            System.out.println("m1 == m2 : " + (m1.getClass() == m3.getClass())); // false, instanceof 사용

            tx.commit();
        }catch(Exception e){
            tx.rollback();
        }finally {
            em.close();
        }

        emf.close();

    }
}
