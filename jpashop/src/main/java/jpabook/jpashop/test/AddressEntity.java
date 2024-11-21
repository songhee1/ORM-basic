package jpabook.jpashop.test;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity(name = "Address")
public class AddressEntity {
    @Id @GeneratedValue
    private Long id; // PK, 값 타입 컬렉션 대신 엔티티(일대다 단방향 매핑)

    private Address address;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Address getAddress() {
        return address;
    }

    public AddressEntity(String city, String street, String zipcode) {
        this.address = new Address(city, street, zipcode);
    }

    public AddressEntity() {
    }
}
