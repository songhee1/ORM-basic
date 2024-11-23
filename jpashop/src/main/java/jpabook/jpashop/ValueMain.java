package jpabook.jpashop;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import java.util.List;
import jpabook.jpashop.test.MemberTest;
import jpabook.jpashop.test.MemberType;
import jpabook.jpashop.test.Team;

public class ValueMain {
    public static void main(String[] args){

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try{
            Team team = new Team();
            team.setName("teamA");
            em.persist(team);

            MemberTest member = new MemberTest();
            member.setAge(10);
            member.setType(MemberType.ADMIN);
            member.setTeam(team);
            member.setUsername("관리자");
            em.persist(member);

            em.flush();
            em.clear();
            // CASE식
            String query = "select "
                + "case when m.age<=10 then '학생요금' "
                + "     when m.age>=60 then '경로요금' "
                + "     else '일반 요금'  "
                + "end "
                + "from MemberTest m";

            List<String> resultList = em.createQuery(query, String.class).getResultList();

            // COALESCE
            String query2 = "select COALESCE(m.username, '이름없는 회원') from MemberTest  m";
            String singleResult = em.createQuery(query2, String.class).getSingleResult();

            // NULLIF
            String query3 = "select NULLIF(m.username, '관리자') from MemberTest  m";
            String singleResult2 = em.createQuery(query3, String.class).getSingleResult();
            System.out.println(singleResult);
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
