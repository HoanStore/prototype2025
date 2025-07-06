package com.hoan.jdk21study.study.jdk11;

/**
 * Nest-Based Access Control은 JDK 11에서 도입된 기능으로,
 * 중첩 클래스(nested class)들 간의
 * private 멤버 접근을 보다 명확하고 효율적으로 허용하기 위한 JVM 수준의 메커니즘이다.
 *
 * jdk8)
 * Inner 클래스가 Outer 클래스의 private 필드에 접근할 수 있음.
 * 자바 컴파일러는 내부적으로 bridge method를 만들어 우회 접근하게 만듦.
 * JVM 입장에선 private을 깨는 방식이라 성능·보안·구현상 문제가 있었음.
 *
 * jdk11)
 * 이제 JVM 레벨에서 Outer와 Inner가 **같은 nest(둥지)**에 있다고 간주함.
 * NestMate라는 개념으로, 이 관계를 메타데이터로 명시함.
 * 결과적으로 bridge method 없이도 private 멤버에 직접 접근 가능하게 변경됨.
 */
public class NestedBaseAccessControl {

    private int secret = 42;

    class Inner {
        void print() {
            System.out.println(secret); // 접근 가능
        }

    }

    public static void main(String[] args) {
        NestedBaseAccessControl nestedBaseAccessControl = new NestedBaseAccessControl();
        Inner inner = nestedBaseAccessControl.new Inner();

        inner.print();
    }

}
