package com.hoan.jdk21study.study.jdk21;

public class StringTemplateExample {
    public static void main(String[] args) {
        String name = "Max";
        String greeting = STR."Hello, \{name}!";

        System.out.println("greeting = " + greeting);
    }
}
