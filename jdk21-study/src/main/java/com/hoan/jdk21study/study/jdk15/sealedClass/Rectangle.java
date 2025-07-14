package com.hoan.jdk21study.study.jdk15.sealedClass;

// Rectangle.java
public final class Rectangle extends SealedClassStudy {
    private final double width, height;

    public Rectangle(double width, double height) {
        this.width = width;
        this.height = height;
    }

    @Override
    public double area() {
        return width * height;
    }
}
