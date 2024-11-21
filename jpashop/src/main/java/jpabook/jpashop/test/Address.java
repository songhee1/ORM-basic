package jpabook.jpashop.test;

import jakarta.persistence.Embeddable;

@Embeddable // 값 타입 정의
public class Address {
    // Address
    String city;
    String street;
    String zipcode;
//    private Parent parent; // 엔티티 가질 수 있음

    //Setter x / private => 객체 참조 공유 방지, 불변으로 생성
    public String getCity() {
        return city;
    }

    public String getStreet() {
        return street;
    }

    public String getZipcode() {
        return zipcode;
    }

    public Address() {
    }

    public Address(String city, String street, String zipcode) {
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }
}
