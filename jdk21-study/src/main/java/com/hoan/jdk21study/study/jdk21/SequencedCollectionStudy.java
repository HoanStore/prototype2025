package com.hoan.jdk21study.study.jdk21;

import java.util.ArrayList;
import java.util.List;
import java.util.SequencedCollection;

public class SequencedCollectionStudy {
    public static void main(String[] args) {
        SequencedCollection<String> list = new ArrayList<>(List.of("A", "B", "C"));

        System.out.println(list.getFirst());  // 출력: A
        System.out.println(list.getLast());   // 출력: C

        if(list instanceof List<String> l) {
            System.out.println(l.get(1));
        }
    }
}
