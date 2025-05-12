package com.hoan.jdk21study.study.jsconceptwithjava;

import java.util.function.Consumer;

public class ClosureInJava {
    public static Consumer<String> greeting(String prefix) {
        return (hello) -> System.out.println(prefix + " " + hello);
    }

    public static void main(String[] args) {
        Consumer<String> sayHi = greeting("고객님");
        sayHi.accept("안녕");
    }
}
