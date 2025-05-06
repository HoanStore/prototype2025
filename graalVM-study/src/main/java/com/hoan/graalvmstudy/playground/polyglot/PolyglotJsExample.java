package com.hoan.graalvmstudy.playground.polyglot;

import org.graalvm.polyglot.*;

public class PolyglotJsExample {
    public static void main(String[] args) {
        try (Context context = Context.create()) {
            context.eval("js", "console.log('Hello from JavaScript!');");
            Value result = context.eval("js", "1 + 2");
            System.out.println("JavaScript 계산 결과: " + result.asInt());
        }
    }
}
