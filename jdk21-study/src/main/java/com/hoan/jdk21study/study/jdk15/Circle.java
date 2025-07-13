package com.hoan.jdk21study.study.jdk15;

// Circle.java
public final class Circle extends SealedClassStudy {
    private final double radius;

    public Circle(double radius) {
        this.radius = radius;
    }

    @Override
    public double area() {
        return Math.PI * radius * radius;
    }
}
