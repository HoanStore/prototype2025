package com.hoan.jdk21study.study.jdk12;

/**
 * Switch Expressions는 JDK 12에서 Preview 기능으로 처음 도입된 후 JDK 14에서 정식 기능이 되었음.
 * 기존 switch 문법보다 **간결하고, 표현식(값을 반환)**으로 사용 가능하다는 점이 핵심.
 */
public class SwitchExpression {

    public static void main(String[] args) {
        jdk8Switch();
        jdk12Switch();
    }

    public static void jdk8Switch() {
        String day = "MONDAY";
        int numLetters;

        switch (day) {
            case "MONDAY":
            case "FRIDAY":
            case "SUNDAY":
                numLetters = 6;
                break;
            case "TUESDAY":
                numLetters = 7;
                break;
            case "THURSDAY":
            case "SATURDAY":
                numLetters = 8;
                break;
            case "WEDNESDAY":
                numLetters = 9;
                break;
            default:
                throw new IllegalStateException("Invalid day: " + day);
        }

        System.out.println(numLetters);  // 출력: 6
    }

    public static void jdk12Switch() {
        String day = "MONDAY";

        int numLetters = switch (day) {
            case "MONDAY", "FRIDAY", "SUNDAY" -> 6;
            case "TUESDAY" -> 7;
            case "THURSDAY", "SATURDAY" -> 8;
            case "WEDNESDAY" -> 9;
            default -> throw new IllegalStateException("Invalid day: " + day);
        };

        System.out.println(numLetters);  // 출력: 6

    }
}
