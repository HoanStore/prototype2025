package com.hoan.todomap.util;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class GoogleEmailSenderTest {

    @Autowired
    private GoogleEmailSender emailSender;


    @Test
    public void emailSenderTest() {

        String fromEmail = "bluepack701@gmail.com"; // 보내는 이메일 주소
        String password = "yguxtgtwnprcydmz"; // 이메일 계정 비밀번호
        String toEmail = "ktm1296@naver.com"; // 받는 이메일 주소

        String subject = "[TEST]";
        String body = "TEST";

        emailSender.sendEmail(fromEmail, password, toEmail, subject, body);
    }
}
