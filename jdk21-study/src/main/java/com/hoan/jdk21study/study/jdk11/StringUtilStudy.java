package com.hoan.jdk21study.study.jdk11;

import java.util.stream.Stream;

public class StringUtilStudy {

    public static void main(String[] args) {
        stringUtilCanUseInJdk11();



    }

    public static void stringUtilCanUseInJdk11() {
        // 1. isBlank()
        boolean blank = "".isBlank();


        // 2. lines() : 문자열을 줄 단위로 나누어 Stream<String> 형태로 반환
        String text = "Hello\nWorld\nJava";
        Stream<String> lines = text.lines();
        lines.forEach(System.out::println);

        // 3. strip(), stripLeading(), stripTrailing() : 유니코드 기준 공백 제거 (기존 trim()은 ASCII 공백만 제거)
        String s = "\u2000 Hello \u2000";

        String strip = s.strip();
        String trim = s.trim();

        System.out.println(strip);
        System.out.println(trim);
        
        // 4. repeat(int count)
        String repeat = "abc".repeat(3);
        System.out.println("repeat = " + repeat);
    }

}
