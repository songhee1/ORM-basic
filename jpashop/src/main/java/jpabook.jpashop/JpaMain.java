package jpabook.jpashop;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Team;

public class JpaMain {
    public static void main(String[] args){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try{
            Team team = new Team();
            team.setName("TeamA");
            em.persist(team);

            Team team2 = new Team();
            team2.setName("TeamB");
            em.persist(team2);

            Member member = new Member();
            member.setName("member1");
            //(과거)member.setTeamId(team.getId()); // 객체 지향적x
            member.setTeam(team);
            em.persist(member);
            em.flush();
            em.clear(); // 영속성 컨텍스트 초기화(find로 SELECT 쿼리 발생 확인)

            Member findMember = em.find(Member.class, member.getId());
            //(과거)Team findTeam = em.find(Team.class, findMember.getTeamId()); // 연관관계x
            Team findTeam = findMember.getTeam();
            System.out.println("findTeam = "+findTeam.getName());

            findMember.setTeam(team2);

            tx.commit();
        }catch(Exception e){
            tx.rollback();
        }finally {
            em.close();
        }

        emf.close();

    }
}
