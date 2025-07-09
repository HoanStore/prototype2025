package com.hoan.jdk21study.study.jdk12;

import java.util.List;

import static java.util.stream.Collectors.*;

public class TeeingCollector {

    public static void main(String[] args) {
        List<Integer> numbers = List.of(1, 2, 3, 4, 5);

        var result = numbers.stream().collect(
                teeing(
                        summingInt(i -> i),
                        counting(),
                        (sum, count) -> sum / count
                )
        );

        System.out.println("result = " + result);
    }
}
