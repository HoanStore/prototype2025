package com.hoan.jdk21study.study.jdk20;

import java.util.concurrent.ThreadFactory;

/**
 * Virtual Threads (JEP 436 - Preview)
 *
 * Blocking I/O를 걱정 없이 쓸 수 있는 경량 스레드
 * 기존 Thread보다 메모리 소비 적고 생성 비용 낮음
 * 기존 API 그대로 사용 가능 (Runnable, ExecutorService 등)
 * TPS가 높은 시스템에서 대규모 연결 처리 (ex. 웹 서버, 크롤러 등)에 탁월
 *
 */
public class VirtualThreads {
    public static void main(String[] args) throws InterruptedException {
        virtualThreadPractice();
        beforeVirtualThread();

    }

    private static void beforeVirtualThread() throws InterruptedException {
        ThreadFactory virtualThreadFactory = Thread.ofVirtual().factory();

        Thread vThread = virtualThreadFactory.newThread(() -> {
            System.out.println("이전 방식으로 생성한 Virtual Thread");
        });

        vThread.start();
        vThread.join();

    }

    private static void virtualThreadPractice() throws InterruptedException {
        Thread vThread = Thread.startVirtualThread(() -> {
            System.out.println("경량 스레드에서 실행");
        });

        vThread.join(); // main이 virtual thread 끝날 때까지 대기
    }
}
