package com.hoan.jdk21study.study.jdk15.sealedClass;

// Triangle.java
public non-sealed class Triangle extends SealedClassStudy {
    private final double base, height;

    public Triangle(double base, double height) {
        this.base = base;
        this.height = height;
    }

    @Override
    public double area() {
        return 0.5 * base * height;
    }
}
