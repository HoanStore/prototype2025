package com.hoan.jdk21study.study.jdk21;

sealed interface Shape permits Circle, Rectangle {}

record Circle(double radius) implements Shape {}
record Rectangle(double width, double height) implements Shape {}

public class PatternMatchingExample {
    static void printArea(Shape shape) {

        switch (shape) {
            case Circle c -> System.out.println("Area: " + (Math.PI * c.radius() * c.radius()));
            case Rectangle r -> System.out.println("Area: " + (r.width() * r.height()));
        }
    }

    public static void main(String[] args) {
        printArea(new Circle(5));
        printArea(new Rectangle(4, 6));
    }
}
