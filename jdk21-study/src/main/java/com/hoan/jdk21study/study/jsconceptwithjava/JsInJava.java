package com.hoan.jdk21study.study.jsconceptwithjava;

import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.Value;

public class JsInJava {
    public static void main(String[] args) {
        try (Context context = Context.newBuilder("js").allowAllAccess(true).build()) {
            context.eval("js", "console.log('Hello from JavaScript on GraalVM!');");

            Value result = context.eval("js", "1 + 2 + 3");
            System.out.println("결과: " + result.asInt());
        }

    }

}
