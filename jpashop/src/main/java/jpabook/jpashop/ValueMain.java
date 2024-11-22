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

            MemberTest member = new MemberTest();
            member.setUsername("member1");
            member.setAge(10);
            em.persist(member);

            em.flush();
            em.clear();

            // 스칼라 타입 프로젝션
            List resultList = em.createQuery(
                    "select distinct m.username, m.age from MemberTest m") // join SELECT
                .getResultList();

            // 1. Object 2. Object[]
            Object o = resultList.get(0);
            Object[] result = (Object[]) o;
            System.out.println("username = "+result[0]); // username
            System.out.println("age = "+result[1]); // age

            //3. TypeQuery
            List<Object[]> resultList2 = em.createQuery(
                    "select distinct m.username, m.age from MemberTest m") // join SELECT
                .getResultList(); // 타입 캐스팅

            //4. new(권장)
            List<MemberDTO> resultList1 = em.createQuery(
                "select new jpabook.jpashop.test.MemberDTO(m.username, m.age) from MemberTest m",
                MemberDTO.class).getResultList();
            MemberDTO memberDTO = resultList1.get(0);
            System.out.println("memberDTO.username = "+ memberDTO.getUsername());
            System.out.println("memberDTO.age = "+ memberDTO.getAge());


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
