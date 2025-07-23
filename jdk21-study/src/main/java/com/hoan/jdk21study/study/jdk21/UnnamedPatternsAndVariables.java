package com.hoan.jdk21study.study.jdk21;

record Point(int x, int y) {}

/**
 * _ 언더스코어로 쓰지 않는 변수나 패턴을 무시할 수 있음.
 * 가독성 개선, 불필요한 변수 선언 제거.
 *
 */
public class UnnamedPatternsAndVariables {
    public static void main(String[] args) {

        UnnamedPatternsAndVariables unnamedPatternsAndVariables = new UnnamedPatternsAndVariables();
        unnamedPatternsAndVariables.printY(new Point(100, 200));
    }

    void printY(Object obj) {
        if (obj instanceof Point(_, int y)) {
            System.out.println("Y좌표는: " + y);
        }
    }
}
