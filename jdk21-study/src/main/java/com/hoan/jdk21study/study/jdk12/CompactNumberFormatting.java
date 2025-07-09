package com.hoan.jdk21study.study.jdk12;

import java.text.NumberFormat;
import java.util.Locale;

public class CompactNumberFormatting {
    public static void main(String[] args) {
        NumberFormat formatter = NumberFormat.getCompactNumberInstance(Locale.US, NumberFormat.Style.SHORT);
        formatter.setMaximumFractionDigits(1);

        System.out.println(formatter.format(1000));     // 출력: 1K
        System.out.println(formatter.format(2500000));  // 출력: 2.5M
    }
}
