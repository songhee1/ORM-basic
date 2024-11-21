package jpabook.jpashop.test;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Parent {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL,orphanRemoval = true) // orphanRemoval : 참조 제거 엔티티 삭제
    List<Child> children = new ArrayList<>();
    /*
    * 고아객체 조건(cascade = CascadeType.ALL/CascadeType.PERSIST + orphanRemoval = true)
    * 1. 부모 엔티티 삭제 (em.remove(parent)) : 부모,자식 모두 DELETE
    * 2. 부모 엔티티와 연관된 자식 엔티티 컬렉션 제거 (parent.getChildren().remove(0)) : 자식 DELETE
    * */

    public void addChild(Child child){
        children.add(child);
        child.setParent(this);
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Child> getChildren() {
        return children;
    }
}
