package com.hoan.jdk21study.study;

import java.util.stream.IntStream;
import java.util.concurrent.*;

public class PrimeCheckerWithVirtualThread {
    public static boolean isPrime(int number) {
        if (number < 2) return false;
        for (int i = 2; i <= Math.sqrt(number); i++) {
            if (number % i == 0) return false;
        }
        return true;
    }

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        ExecutorService executor = Executors.newFixedThreadPool(4); // 4개의 스레드 사용
        int[] numbers = {31, 37, 41, 43, 47, 51, 53, 59};

        for (int num : numbers) {
            executor.submit(() -> {
                System.out.println(num + " is prime? " + isPrime(num) + " - " + Thread.currentThread().getName());
            });
        }

        executor.shutdown();
    }


}
