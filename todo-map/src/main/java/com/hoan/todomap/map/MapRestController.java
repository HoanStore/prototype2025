package com.hoan.todomap.map;

import com.hoan.todomap.dto.EmailRequest;
import com.hoan.todomap.util.EmailSender;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class MapRestController {

    private EmailSender emailSender;

    public MapRestController(EmailSender emailSender) {
        this.emailSender = emailSender;
    }

    @PostMapping("/email")
    public void emailSend(@RequestBody EmailRequest emailRequest) {

        String fromEmail = "bluepack701@gmail.com"; // 보내는 이메일 주소
        String password = "yguxtgtwnprcydmz"; // 이메일 계정 비밀번호
        String toEmail = "ktm1296@naver.com"; // 받는 이메일 주소

        String subject = emailRequest.getSubject();
        String body = emailRequest.getBody();

        emailSender.sendEmail(fromEmail, password, toEmail, subject, body);
    }


}
