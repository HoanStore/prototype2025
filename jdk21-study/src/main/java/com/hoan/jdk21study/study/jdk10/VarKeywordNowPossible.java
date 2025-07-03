package com.hoan.jdk21study.study.jdk10;

import java.util.ArrayList;
import java.util.List;

public class VarKeywordNowPossible {

    public static void main(String[] args) {

    }

    public void varNotPossibleInJdk8() {
        List<String> list = new ArrayList<>();
    }

    public void varNotPossibleInJdk10() {
        var list = new ArrayList<String>();  // 컴파일러가 list의 타입을 추론함
        for (var item : list) {
            System.out.println(item);
        }
    }
}
