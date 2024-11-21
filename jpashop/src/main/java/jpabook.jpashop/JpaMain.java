package jpabook.jpashop;

import com.sun.source.util.ParameterNameProvider;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import java.util.List;
import jdk.swing.interop.SwingInterOpUtils;
import jpabook.jpashop.domain.Member;
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
            Child child1 = new Child();
            Child child2 = new Child();

            Parent parent = new Parent();
            parent.addChild(child1);
            parent.addChild(child2);

            em.persist(parent); // CascadeType.ALL/PERSIST로 부모,자식 INSERT
            em.flush();
            em.clear();

            Parent findParent = em.find(Parent.class, parent.getId());

            findParent.getChildren().remove(0); // 참조 제거된 엔티티 삭제

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
