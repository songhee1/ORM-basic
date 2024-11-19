package jpabook.jpashop;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import java.util.List;
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

            Member member = new Member();
            member.setName("member1");
            member.setTeam(team);
            em.persist(member);

            //5. 객체지향적으로 양쪽 모두 세팅 필요
            team.getMembers().add(member);
            /*
            * 1.
            * (필요X) team.getMembers().add(member)
            * -> (문제X) JPA 동작시 TEAM_ID로 members 세팅되기 때문
            * */

            //2. 아래 두 줄이 없다면?
            /*em.flush();
            em.clear();*/

            Team findTeam = em.find(Team.class, team.getId()); //3. 1차 캐시 : member 없는 채로 team 객체 저장
            List<Member> members = findTeam.getMembers();
            for (Member m : members) {
                System.out.println("m = "+m.getName()); // 4. 출력x
            }

            tx.commit();
        }catch(Exception e){
            tx.rollback();
        }finally {
            em.close();
        }

        emf.close();

    }
}
