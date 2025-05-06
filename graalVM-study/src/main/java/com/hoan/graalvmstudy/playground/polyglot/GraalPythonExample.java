package com.hoan.graalvmstudy.playground.polyglot;

import org.graalvm.polyglot.*;

public class GraalPythonExample {
    public static void main(String[] args) {
        try (Context context = Context.create()) {
            context.eval("python", "print('Hello from Python in GraalVM!')");
        }
    }
}
