package jpabook.jpashop.test;

import static jakarta.persistence.CascadeType.ALL;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class MemberTest {
    @Id @GeneratedValue
    private Long id;
    private String username;
    private int age;
    @ManyToOne(fetch = FetchType.LAZY) // 즉시로딩-MemberTest join query시 TEAM SELECT 쿼리 발생
    @JoinColumn(name = "TEAM_ID")
    private Team team;

    /*@OneToMany(mappedBy = "member")
    List<OrderTest> orderList = new ArrayList<>();*/

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) { // 연관관계 편의 메서드
        this.team = team;
        team.getMemberList().add(this);
    }

    @Override
    public String toString() {
        return "MemberTest{" +
            "id=" + id +
            ", username='" + username + '\'' +
            ", age=" + age +
            '}'; // 양방향 되면 안됨
    }
}
