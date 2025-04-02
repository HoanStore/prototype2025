package com.hoan.pagingexcel.config;


import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;


@ControllerAdvice
public class GlobalExceptionHandler {


    // 404 에러 처리 (잘못된 URL 요청 등)
    @ExceptionHandler(Exception.class)
    public String handleException(ResponseStatusException e, Model model) {
        if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
            model.addAttribute("message", "페이지를 찾을 수 없습니다.");
            return "error/404"; // templates/error/404.html
        }
        return "error/500"; // 기타 상태 코드 처리
    }
}
