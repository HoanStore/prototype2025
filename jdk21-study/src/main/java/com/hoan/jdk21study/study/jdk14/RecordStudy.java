package com.hoan.jdk21study.study.jdk14;

/**
 * JDK 14에서 도입된 **record**는 간결한 방식으로
 * **불변 데이터 객체(Immutable Data Class)**를 선언하는 기능이다.
 * getter, toString(), equals(), hashCode() 등을 자동 생성해주기 때문에
 * DTO, VO, Value Object처럼 데이터를 담기 위한 클래스를 매우 간단하게 정의할 수 있다.
 */

record Person(String name, int age) {}

public class RecordStudy {

    public static void main(String[] args) {
        Person hoan = new Person("Hoan", 32);
        System.out.println("hoan.name() = " + hoan.name());
        System.out.println("hoan.age() = " + hoan.age());
    }
}
