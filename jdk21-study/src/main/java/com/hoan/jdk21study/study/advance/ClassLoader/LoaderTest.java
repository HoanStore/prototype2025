package com.hoan.jdk21study.study.advance.ClassLoader;

public class LoaderTest {
    public static void main(String[] args) throws Exception {
        MyClassLoader loader = new MyClassLoader();
        Class<?> clazz = loader.loadClass("com.example.MyClass");
        Object instance = clazz.getDeclaredConstructor().newInstance();
        System.out.println("Loaded by: " + clazz.getClassLoader());
    }
}
