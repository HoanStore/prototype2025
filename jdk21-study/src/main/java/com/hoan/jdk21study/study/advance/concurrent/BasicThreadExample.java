package com.hoan.jdk21study.study.advance.concurrent;

public class BasicThreadExample {
    public static void main(String[] args) {
        Runnable task = () -> {
            for (int i = 1; i <= 5; i++) {
                System.out.println(Thread.currentThread().getName() + " 출력: " + i);
                try {
//                    Thread.sleep(0); // 100ms 대기 (다른 스레드에게 CPU 양보)
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        Thread thread1 = new Thread(task, "스레드A");
        Thread thread2 = new Thread(task, "스레드B");

        thread1.start();
        thread2.start();
    }
}

