package jpabook.jpashop;

import jakarta.persistence.criteria.CriteriaBuilder.In;

public class ValueMain {
    public static void main(String[] args){

        // 값 타입
        // 기본 값 타입
        int a = 10;
        int b = a;
        b = 20;

        System.out.println("a = "+a); // 10
        System.out.println("b = "+b); // 20
        //공유x

        // 래퍼 클래스
        Integer ai = new Integer(20);
        Integer bi = ai; // 참조값 넘어가 같은 인스턴스 공유, 변경x

        System.out.println("a = "+ai); // 10
        System.out.println("b = "+bi); // 10
    }
}
