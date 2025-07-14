package com.hoan.jdk21study.study.jdk15.sealedClass;


/**
 * sealed 클래스는 permits 키워드를 통해 상속을 허용할 클래스 명시
 * final, sealed, non-sealed 중 하나를 서브클래스에 반드시 선언해야 함
 *
 * final: 더 이상 상속 불가
 * non-sealed: 자유롭게 상속 허용
 * sealed: 다시 하위 클래스를 제한 가능
 */
// Shape.java
public sealed abstract class SealedClassStudy permits Circle, Rectangle, Triangle {
    public abstract double area();
}

