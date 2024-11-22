package jpabook.jpashop;

import com.sun.source.util.ParameterNameProvider;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import java.util.List;
import java.util.Set;
import jdk.swing.interop.SwingInterOpUtils;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.test.Address;
import jpabook.jpashop.test.AddressEntity;
import jpabook.jpashop.test.Child;
import jpabook.jpashop.test.MemberTest;
import jpabook.jpashop.test.Parent;
import jpabook.jpashop.test.Team;
import org.hibernate.Hibernate;
import org.hibernate.jpa.internal.PersistenceUnitUtilImpl;

public class JpaMain {
    public static void main(String[] args){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try{
            MemberTest member = new MemberTest();
            member.setUserName("member1");
            member.setHomeAddress(new Address("homeCity", "street", "zipcode"));

            member.getFavoriteFoods().add("치킨");
            member.getFavoriteFoods().add("족발");
            member.getFavoriteFoods().add("피자");

            AddressEntity ade1 = new AddressEntity("old1", "street", "zipcode");
            AddressEntity ade2 = new AddressEntity("old2", "street", "zipcode");
            member.getAddressHistory().add(ade1); //ADDRESS 외래키 UPDATE(일대다 단방향)
            member.getAddressHistory().add(ade2); //ADDRESS 외래키 UPDATE(일대다 단방향)
            //값 타입을 엔티티로 승급, 수정가능(권장)

            em.persist(member); // persist 1회로 관련 값 타입 갯수대로 각각 INSERT, 라이프사이클이 member에 종속(모두 값 타입)

            em.flush();
            em.clear();
            System.out.println("===구분선===");
            MemberTest findMember = em.find(MemberTest.class, member.getId()); // SELECT MEMBER, 지연로딩
            AddressEntity changeAddress = findMember.getAddressHistory().get(0);
            changeAddress.setCity("newnewnew");

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
