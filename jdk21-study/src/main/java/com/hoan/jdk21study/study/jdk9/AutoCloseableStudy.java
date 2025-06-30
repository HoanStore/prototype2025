package com.hoan.jdk21study.study.jdk9;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * JDK 8에서는 try-with-resources 문 안에서 직접 선언한 변수만 자동으로 close() 호출이 됐음.
 * JDK 9부터는 자원을 외부에서 선언 가능
 * 자원을 try 바깥에서 먼저 준비해두고, try (reader)에 넘겨주는 형태
 * 다만, 해당 객체는 final 또는 실질적으로 final(더 이상 변경 안 되는 변수)이어야 함.
 */
public class AutoCloseableStudy {

    public static void main(String[] args) {

    }

    public void jdk8Way() throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader("test.txt"))) {
            System.out.println(reader.readLine());
        }

    }

    public void jdk9Way() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("test.txt"));
        try (reader) {
            System.out.println(reader.readLine());
        }

    }
}
