package jpabook.jpashop;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
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

            MemberTest member = new MemberTest();
            member.setUsername("member1");
            member.setAge(10);
            em.persist(member);

            TypedQuery<MemberTest> query = em.createQuery("select m from MemberTest m",
                MemberTest.class);// 두번째 매개변수 = 엔티티 클래스명, 반화 클래스 명확, TypedQuery
            Query query2 = em.createQuery("select m.username, m.age from MemberTest m");// 두번째 매개변수 = 엔티티 클래스명

            List<MemberTest> resultList = query.getResultList(); // 컬렉션 반환
            MemberTest result = query.getSingleResult();// 반환 1개, 그렇지않으면 Exception
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
