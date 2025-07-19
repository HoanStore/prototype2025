package com.hoan.jdk21study.study.jdk19;

public class RecordPatternExample {
    // 레코드 정의
    record Person(String name, int age) {}

    public static void main(String[] args) {
        Object obj = new Person("Keunwan", 30);

        if (obj instanceof Person(String name, int age)) {
            System.out.println("이름: " + name);
            System.out.println("나이: " + age);
        } else {
            System.out.println("Person 타입이 아님");
        }
    }
}
