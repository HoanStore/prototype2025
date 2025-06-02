package com.hoan.jdk21study.study.advance.ClassLoader;


public class MyClassLoader extends ClassLoader {
    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        // 클래스 바이트코드를 직접 읽고 defineClass()로 클래스 정의하기
        byte[] classData = loadClassData(name);
        if (classData == null) {
            throw new ClassNotFoundException();
        }
        return defineClass(name, classData, 0, classData.length);
    }

    private byte[] loadClassData(String className) {
        // 클래스 파일(.class) 경로를 찾아 바이트코드를 읽는 코드 구현
        // 예를 들어, 파일시스템에서 읽기
        // return Files.readAllBytes(Paths.get(...));
        return null; // 구현 필요
    }

}
