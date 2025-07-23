package com.hoan.jdk21study.study.jdk21;

import java.util.concurrent.Executors;
import java.util.stream.IntStream;

public class VirtualThread {

    public static void main(String[] args) {

        try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
            IntStream.range(0, 5).forEach(i ->
                    executor.submit(() -> {
                        Thread.sleep(1000);
                        System.out.println("작업 " + i + " 완료: " + Thread.currentThread());
                        return null;
                    })
            );
        }



    }
}
