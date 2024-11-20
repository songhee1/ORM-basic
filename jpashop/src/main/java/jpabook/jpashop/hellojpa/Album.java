package jpabook.jpashop.hellojpa;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("A") // DiscriminatorColumn값 지정
public class Album extends Item{
    private String artist;
}
