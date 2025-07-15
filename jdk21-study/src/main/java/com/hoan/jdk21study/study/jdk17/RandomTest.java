package com.hoan.jdk21study.study.jdk17;

import java.security.SecureRandom;
import java.util.Random;
import java.util.random.RandomGenerator;
import java.util.random.RandomGeneratorFactory;
import java.util.stream.IntStream;

public class RandomTest {
    public static void main(String[] args) {

        prngInJdk8();
        System.out.println();
        prngInJdk17();
        System.out.println();
        cfSecureRandom();

    }


    private static void prngInJdk8() {
        Random random = new Random(42); // seed 고정 가능

        // 1개 난수
        // Random 클래스는 seed 값에 따라 항상 동일한 난수 시퀀스를 생성하는 **의사 난수 생성기 (PRNG)**.
        //즉, new Random(42)를 호출하면 nextInt(100)은 항상 처음에는 같은 값을 반환해. 그게 바로 30이라는 숫자.
        int one = random.nextInt(100);
        System.out.println("난수: " + one);

        // 스트림 기반 난수 5개 생성 (10~99)
        IntStream.generate(() -> 10 + random.nextInt(90))
                .limit(5)
                .forEach(n -> System.out.print(n + " "));
    }

    private static void prngInJdk17() {
        // 고급 PRNG 생성
        // 성능 중심의 비오안 난수 생성기
        RandomGenerator rng = RandomGeneratorFactory.of("L64X256MixRandom").create();

        // 단일 난수
        int randNum = rng.nextInt(100);  // 0~99
        System.out.println("난수: " + randNum);

        // 스트림 기반 난수
        IntStream randStream = rng.ints(5, 10, 100);  // 10~99 사이 난수 5개
        randStream.forEach(n -> System.out.print(n + " "));
    }


    /**
     * 보안용 (세션, 암호키 등)
     *
     * 보안이 필요한 경우 아래 SecureRandom 사용
     */
    private static void cfSecureRandom() {
        SecureRandom secureRandom = new SecureRandom();
        byte[] token = new byte[16];
        secureRandom.nextBytes(token);

        for (byte b : token) {
            System.out.printf("%02x", b);  // 16진수 출력
        }

    }
}
