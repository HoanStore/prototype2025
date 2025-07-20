package com.hoan.jdk21study.study.jdk20;

import java.lang.ScopedValue;

public class ScopedValueExample {

    // ScopedValue 정의 (ThreadLocal과 비슷하게 정적 선언)
    static final ScopedValue<String> USER_ID = ScopedValue.newInstance();

    public static void main(String[] args) {
        // ScopedValue를 바인딩하고 해당 범위 내에서 실행
        ScopedValue.where(USER_ID, "user123").run(() -> {
            System.out.println("로그인한 사용자 ID: " + USER_ID.get());

            // 새로운 Virtual Thread에서 접근
            try {
                Thread.startVirtualThread(() -> {
                    // 상위 컨텍스트에서 내려온 ScopedValue 값이 유효함
                    System.out.println("Virtual Thread에서 사용자 ID: " + USER_ID.get());
                }).join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        // ScopedValue 범위 밖에서는 예외 발생
        try {
            System.out.println("바깥에서 접근: " + USER_ID.get());
        } catch (IllegalStateException e) {
            System.out.println("바깥에서 USER_ID 접근 시 예외 발생: " + e);
        }
    }
}

