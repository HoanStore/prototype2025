package com.hoan.jdk21study.study;

import java.lang.foreign.*;
import java.lang.invoke.MethodHandle;

public class FFMExample {
    public static void main(String[] args) throws Throwable {
        Linker linker = Linker.nativeLinker();
        SymbolLookup stdlib = linker.defaultLookup();

        MethodHandle strlen = linker.downcallHandle(
                stdlib.find("strlen").orElseThrow(),
                FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS)
        );

        try (Arena arena = Arena.ofConfined()) {
            MemorySegment cString = arena.allocateUtf8String("Hello, FFM!");
            int length = (int) strlen.invoke(cString);
            System.out.println("String length: " + length);
        }


    }
}