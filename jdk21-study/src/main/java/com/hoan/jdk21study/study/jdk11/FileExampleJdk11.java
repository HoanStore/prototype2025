package com.hoan.jdk21study.study.jdk11;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileExampleJdk11 {
    public static void main(String[] args) throws IOException {

        fileExampleJdk8();
        fileExampleJdk11();

    }

    /**
     * 바이트 배열을 직접 다루어야 하고, 문자열 <-> 바이트 변환 코드가 추가됨
     */
    private static void fileExampleJdk8() throws IOException {
        Path path = Paths.get("jdk8Example.txt");

        // 파일에 문자열 쓰기
        Files.write(path, "Hello\nJava 8".getBytes(StandardCharsets.UTF_8));

        // 파일에서 문자열 읽기
        byte[] bytes = Files.readAllBytes(path);
        String content = new String(bytes, StandardCharsets.UTF_8);

        System.out.println(content);
    }

    private static void fileExampleJdk11() throws IOException {
        Path path = Paths.get("jdk11Example.txt");

        Files.writeString(path, "Hello\nJava 11");

        String content = Files.readString(path);

        System.out.println("content = " + content);

    }
}
