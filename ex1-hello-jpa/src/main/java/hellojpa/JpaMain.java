package hellojpa;

import jakarta.persistence.*;

public class JpaMain {

    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        //EntityManager : 고객 요청이 올때마다 생성해 사용 - 공유x
        EntityManager em = emf.createEntityManager();
        //code
        EntityTransaction tx = em.getTransaction();
        tx.begin(); // db 트랜잭션 시작
        try{
            Member findMember = em.find(Member.class, 1L);
            System.out.println("findMember.id = "+findMember.getId());
            System.out.println("findMember.name = "+findMember.getName());

            Member findMember2 = em.find(Member.class, 2L);
            em.remove(findMember2); // delete 쿼리 및 삭제

            findMember.setName("HelloJPA");
            //em.persist로 저장하지 않아도 됨
            /*
            * 이유: JPA를 통해서 엔티티를 가져오면 JPA에서 엔티티를 관리하게 됨
            * 변경이 되었는지 등 트랜잭션을 체크함.
            * 변경된 부분이 있다면 UPDATE 쿼리 날리고 transaction commit 날림
            * JPA의 모든 DATA 변경은 트랜잭션 안에서 실행
            * */
            tx.commit();
        }catch(Exception e) {
            tx.rollback();
        }finally{
            em.close();
        }

        emf.close();
    }
}
