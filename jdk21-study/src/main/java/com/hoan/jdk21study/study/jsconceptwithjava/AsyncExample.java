package com.hoan.jdk21study.study.jsconceptwithjava;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AsyncExample {

    public void oldJdkAsync() {
        Thread thread = new Thread(() -> {
            System.out.println("[OLD JDK] 비동기 작업 시작 ");
        });
        thread.start();
    }

    public void afterJdk5Async() {
        ExecutorService executor = Executors.newFixedThreadPool(2);
        executor.submit(() -> {
            System.out.println("[after JDK5] 비동기 작업 수행 중...");
        });
        executor.shutdown();
    }

    public void afterJdk8Async() {
        CompletableFuture.supplyAsync(() -> {
            return "[after JDK8] Hello, ";
        }).thenApply(result -> {
            return result + "World!";
        }).thenAccept(System.out::println);
    }


    public static void main(String[] args) {
        AsyncExample asyncExample = new AsyncExample();

        asyncExample.oldJdkAsync();
        asyncExample.afterJdk5Async();
        asyncExample.afterJdk8Async();
    }

}
