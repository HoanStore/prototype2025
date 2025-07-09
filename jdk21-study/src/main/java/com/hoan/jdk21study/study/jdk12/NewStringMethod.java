package com.hoan.jdk21study.study.jdk12;

public class NewStringMethod {

    public static void main(String[] args) {
        String block = "line1\nline2";
        System.out.println(block.indent(10)); // 각 줄 앞에 2칸 공백 추가

        String transformed = "hello"
                .transform(s -> s + " world")
                        .toUpperCase();
        System.out.println(transformed); // HELLO WORLD

    }
}
