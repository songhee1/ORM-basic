package jpabook.jpashop.test;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Team {
    @Id @GeneratedValue
    private Long id;
    @OneToMany(mappedBy = "team")
    List<MemberTest> memberTests = new ArrayList<>();
}
