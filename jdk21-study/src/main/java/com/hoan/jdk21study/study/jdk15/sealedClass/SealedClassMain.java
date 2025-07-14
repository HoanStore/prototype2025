package com.hoan.jdk21study.study.jdk15.sealedClass;

public class SealedClassMain {
    public static void main(String[] args) {
        Circle circle = new Circle(2.0);


        double area = circle.area();


        System.out.println("area = " + area);
    }
}
