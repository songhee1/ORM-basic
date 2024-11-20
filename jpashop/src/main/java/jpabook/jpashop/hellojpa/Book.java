package jpabook.jpashop.hellojpa;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("B") // DiscriminatorColumn값 지정
public class Book extends Item{
    private String author;
    private String isbn;
}
