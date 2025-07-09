package com.hoan.jdk21study.study.jdk12;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 *  Files.mismatch(file1, file2)는 두 파일이 내용이 완전히 같으면 -1L을 반환
 *
 *  다른 부분이 있으면 처음 다른 바이트의 위치(offset)를 반환해.
 *
 */
public class FileMismatchAPI {

    public static void main(String[] args) throws IOException {
        Path file1 = Paths.get("file1.txt");
        Path file2 = Paths.get("file2.txt");
        long mismatch = Files.mismatch(file1, file2);
        System.out.println(mismatch); // -1이면 동일
    }
}
