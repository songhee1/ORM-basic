package jpabook.jpashop;

import jpabook.jpashop.test.Address;

public class ValueMain {
    public static void main(String[] args){

        int a = 10;
        int b = 10;

        System.out.println("a == b "+ (a== b)); // true

        Address address1 = new Address("city", "street", "zipcode");
        Address address2 = new Address("city", "street", "zipcode");

        System.out.println("address1 == address2 " + (address1 == address2)); // false
        System.out.println("address1 == address2 " + (address1.equals(address2))); // false, equals 기본이 == 비교(재정의 후 true)
    }
}
