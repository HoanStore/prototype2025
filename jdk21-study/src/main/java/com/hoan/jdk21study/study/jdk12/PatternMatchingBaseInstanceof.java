package com.hoan.jdk21study.study.jdk12;

/**
 * obj instanceof String s
 *
 * obj가 String 타입이면
 * 그 값을 자동으로 s라는 변수에 바인딩
 *
 * 따로 (String) obj와 같은 캐스팅을 하지 않아도 됨
 */
public class PatternMatchingBaseInstanceof {
    public static void main(String[] args) {
//        Object obj = "Hello, Pattern Matching!";
        Object obj = new Object();

        if (obj instanceof String s) {
            System.out.println("문자열 길이: " + s.length());
        } else {
            System.out.println("문자열이 아님");
        }

    }
}
