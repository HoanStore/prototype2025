package com.hoan.jdk21study.study.jdk13;

public class TextBlocks {
    public static void main(String[] args) {

        multipleStringLineInJdk8();
        multipleStringLineInJdk13();
        


    }

    private static void multipleStringLineInJdk13() {

        String html = """
                <html>
                    <body>
                        <p>Hello, world</p>
                    </body>
                </html>
                """;


        System.out.println("html = " + html);
    }

    private static void multipleStringLineInJdk8() {
        String html =
                "<html>\n" +
                        "    <body>\n" +
                        "        <p>Hello, world</p>\n" +
                        "    </body>\n" +
                        "</html>\n";


        System.out.println("html = " + html);
        
    }
}
