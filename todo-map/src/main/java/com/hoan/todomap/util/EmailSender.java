package com.hoan.todomap.util;

import org.springframework.stereotype.Component;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Component
public class EmailSender {


    public void sendEmail(String fromEmail, String password, String toEmail, String subject, String body) {
        // 이메일 서버 설정
        String host = "smtp.gmail.com";
        String port = "587";

        // SMTP 서버에 대한 속성 설정
        Properties properties = new Properties();
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", port);
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");

        // 인증 정보 설정
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromEmail, password);
            }
        });

        try {
            // 이메일 메시지 작성
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(fromEmail));  // 발신자 이메일
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));  // 수신자 이메일
            message.setSubject(subject);  // 이메일 제목
            message.setText(body);  // 이메일 본문

            // 이메일 전송
            Transport.send(message);
            System.out.println("Email sent successfully!");
        } catch (MessagingException e) {
            e.printStackTrace();
            System.out.println("Failed to send email.");
        }
    }
}

