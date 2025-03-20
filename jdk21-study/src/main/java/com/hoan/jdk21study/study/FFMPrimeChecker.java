package com.hoan.jdk21study.study;

import java.lang.foreign.*;
import java.lang.invoke.MethodHandle;
import java.nio.file.Path;
import java.util.Random;

public class FFMPrimeChecker {
    public static void main(String[] args) throws Throwable {
        Linker linker = Linker.nativeLinker();

        // 🔹 네이티브 라이브러리 로드 (절대 경로 또는 상대 경로 사용 가능)
        SymbolLookup primeLib = SymbolLookup.libraryLookup(Path.of("/Users/keunwan/hoan_workspace/prototype2025/jdk21-study/src/main/java/com/hoan/jdk21study/study/native_lib/libprime.dylib"), Arena.global());

        MethodHandle isPrime = linker.downcallHandle(
                primeLib.find("is_prime").orElseThrow(),
                FunctionDescriptor.of(ValueLayout.JAVA_BOOLEAN, ValueLayout.JAVA_INT)
        );

        boolean result = (boolean) isPrime.invoke(4);
        System.out.println("FFM API Result: " + result);

        int[] ints = generateRandomIntArray(1000);

        /**
         * C 성능 테스트
         */
        long startTime1 = System.nanoTime();
        for(int i=0; i<ints.length; i++) {
            isPrime.invoke(ints[i]);
        }
        long endTime1 = System.nanoTime();
        System.out.println("C: "+(endTime1-startTime1));

        /**
         * JAVA 성능 테스트
         */
        long startTime2 = System.nanoTime();
        for(int i=0; i<ints.length; i++) {
            javaIsPrime(ints[i]);
        }
        long endTime2 = System.nanoTime();
        System.out.println("JAVA: "+(endTime2-startTime2));


    }

    public static int[] generateRandomIntArray(int n) {
        Random random = new Random();
        int[] array = new int[n];

        for (int i = 0; i < n; i++) {
            array[i] = random.nextInt(Integer.MAX_VALUE); // 0 ~ Integer.MAX_VALUE
        }

        return array;
    }


    public static boolean javaIsPrime(int n) {
        if (n < 2) return false;
        for (int i = 2; i * i <= n; i++) {
            if (n % i == 0) return false;
        }
        return true;
    }


}

