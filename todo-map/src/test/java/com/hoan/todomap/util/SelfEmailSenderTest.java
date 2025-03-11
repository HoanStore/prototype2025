package com.hoan.todomap.util;

import jakarta.mail.MessagingException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SelfEmailSenderTest {

    @Autowired
    private SelfEmailSender selfEmailSender;

    @Test
    public void sendEmail() throws MessagingException {
        String to = "ktm1296@naver.com";
        String subject = "[테스트] 자체 SMTP 서버";
        String text = "안녕하세요!";

        selfEmailSender.sendEmail(to, subject, text);
    }
}
