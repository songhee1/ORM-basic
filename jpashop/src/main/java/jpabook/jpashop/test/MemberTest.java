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
    @Column(name = "MEMBER_ID")
    private Long id;
    private String userName;
    @Embedded
    private Address homeAddress;

    @ElementCollection// 값 타입 컬렉션(일대다로 별도 테이블 생성)
    @CollectionTable(name = "FAVORITE_FOOD",// 매핑 테이블명
        joinColumns = @JoinColumn(name = "MEMBER_ID") // 외래키
    )
    @Column(name = "FOOD_NAME")
    private Set<String> favoriteFoods = new HashSet<>();

    /*@ElementCollection// 값 타입 컬렉션
    @CollectionTable(name = "ADDRESS",// 매핑 테이블명
        joinColumns = @JoinColumn(name = "MEMBER_ID") // 외래키
    )
    private List<Address> addressHistory = new ArrayList<>();
*/

    // 값 타입 컬렉션 대안-엔티티 매핑(일대다 단방향)
    @OneToMany(cascade = ALL, orphanRemoval = true) // 권장
    @JoinColumn(name = "MEMBER_ID")
    private List<AddressEntity> addressHistory = new ArrayList<>();
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }



    public void setUserName(String name) {
        this.userName = name;
    }
    public Address getHomeAddress() {
        return homeAddress;
    }

    public String getUserName() {
        return userName;
    }

    public void setHomeAddress(Address homeAddress) {
        this.homeAddress = homeAddress;
    }

    public Set<String> getFavoriteFoods() {
        return favoriteFoods;
    }

    public void setFavoriteFoods(Set<String> favoriteFoods) {
        this.favoriteFoods = favoriteFoods;
    }

    public List<AddressEntity> getAddressHistory() {
        return addressHistory;
    }

    public void setAddressHistory(List<AddressEntity> addressHistory) {
        this.addressHistory = addressHistory;
    }
}
