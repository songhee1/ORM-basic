package jpabook.jpashop.test;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.annotations.BatchSize;

@Entity
public class Team {
    @Id @GeneratedValue
    private Long id;

    private String name;
    @BatchSize(size = 100)
    @OneToMany(mappedBy = "team")
    private List<MemberTest> members = new ArrayList<>();

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

    public List<MemberTest> getMemberList() {
        return members;
    }

    public void setMemberList(List<MemberTest> memberList) {
        this.members = memberList;
    }
}
