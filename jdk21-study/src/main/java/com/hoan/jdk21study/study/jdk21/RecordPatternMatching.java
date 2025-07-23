record point(int x, int y) {}

static void printPoint(Object obj) {
    /**
     * 1. instanceof은 객체가 특정 클래스의 인스턴스인지 검사하는 Java 연산자임.
     * 2. jdk16 이후부터는 "패턴 매칭"을 지원함. 즉, 아래처럼 쓸 수 있음.
     * [AS-IS]
     * if (myAnimal instanceof Dog) {
     *     Dog myDog = (Dog) myAnimal;  // 명시적 형 변환
     *     myDog.bark();
     * }
     * [TO-BE]
     * if (myAnimal instanceof Dog myDog) {  // 자동 형 변환
     *     myDog.bark();
     * }
     */
    if (obj instanceof point(int x, int y)) {  // 자동으로 필드 분해
        System.out.println("좌표: " + x + ", " + y);
    }
}

public static void main(String[] args) {
    printPoint(new point(3, 4));  // 출력: 좌표: 3, 4


}



