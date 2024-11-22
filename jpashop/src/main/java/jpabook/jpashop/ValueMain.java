package jpabook.jpashop;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
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

            //Criteria 사용 - 자바코드(컴파일오류 지원), 동적쿼리, 권장x(유지보수 어려움)
            CriteriaBuilder cb = em.getCriteriaBuilder(); // 자바 표준 스펙
            CriteriaQuery<MemberTest> query = cb.createQuery(MemberTest.class);
            Root<MemberTest> m = query.from(MemberTest.class);
            CriteriaQuery<MemberTest> cq = query.select(m)
                .where(cb.notEqual(m.get("userName"), "kim"));
            em.createQuery(cq).getResultList();

            MemberTest member = new MemberTest();
            member.setUserName("member1");
            em.persist(member);

            // [주의]DB connection 획득해 query 발생시 (JPA 무관)
            //(강제 플러쉬) em.flush()
            // dbconn.executeQuery("select * from member") // 엔티티 객체는 영속성 컨텍스트에 존재, 결과 0(커밋 후 플러쉬), 강제 플러쉬 필요

            // flush 호출 1. commit 직전 2. EntityManager 통한 query 발생
            List resultList = em.createNativeQuery(
                "select MEMBER_ID, city, street, zipcode from MEMBER").getResultList(); // 커밋x, query 발생-플러쉬-INSERT, SELECT


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
