package jpabook.jpashop;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import java.util.List;
import jpabook.jpashop.test.Address;
import jpabook.jpashop.test.MemberDTO;
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
            member.setUsername("memberA");
            member.setType(MemberType.ADMIN);
            member.setAge(10);

            member.setTeam(team);
            em.persist(member);

            em.flush();
            em.clear();

            String query = "select m.username, 'HELLO', TRUE, FALSE, true, false from MemberTest m "+
                "where m.type = jpabook.jpashop.test.MemberType.ADMIN"; // 패키지명 기입

            String query2 = "select m.username, 'HELLO', TRUE, FALSE, true, false from MemberTest m "+
                "where m.type = :userType";
            List<Object[]> result = em.createQuery(
                    query)
                .getResultList();

            List<Object[]> result2 = em.createQuery(
                    query2).setParameter("userType", MemberType.ADMIN) // 파라미터 매핑 권장-이넘타입
                .getResultList();


            for (Object[] objects : result) {
                System.out.println("objects = "+ objects[0]);
                System.out.println("objects = "+ objects[1]);
                System.out.println("objects = "+ objects[2]);
            }

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
