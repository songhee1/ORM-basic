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
import jpabook.jpashop.test.Team;

public class ValueMain {
    public static void main(String[] args){

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try{
            for(int i=1;i<=100;i++){
                MemberTest member = new MemberTest();
                member.setUsername("member"+i);
                member.setAge(i);
                em.persist(member);
            }

            em.flush();
            em.clear();

            List<MemberTest> result = em.createQuery(
                    "select m from MemberTest m order by m.age desc", MemberTest.class)
                .setFirstResult(0)
                .setMaxResults(10)
                .getResultList();

            System.out.println("result size = "+result.size());
            for (MemberTest memberTest : result) {
                System.out.println(memberTest);
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
