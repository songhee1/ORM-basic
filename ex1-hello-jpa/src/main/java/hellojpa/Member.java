package hellojpa;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Member {
    @Id
    private Long id;
    private String name;

    //Getter, Setter
}
