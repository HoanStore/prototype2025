package com.hoan.todomap.dto;


public class EmailRequest {

    private String fromEmail;
    private String password;
    private String toEmail;
    private String subject;
    private String body;

    public String getFromEmail() {
        return fromEmail;
    }

    public String getPassword() {
        return password;
    }

    public String getToEmail() {
        return toEmail;
    }

    public String getSubject() {
        return subject;
    }

    public String getBody() {
        return body;
    }

    public void setFromEmail(String fromEmail) {
        this.fromEmail = fromEmail;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setToEmail(String toEmail) {
        this.toEmail = toEmail;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
