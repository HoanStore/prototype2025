package com.hoan.pagingexcel.config;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

import java.util.Locale;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    // 기본 로케일을 Accept-Language 헤더 기반으로 설정
    @Bean
    public LocaleResolver localeResolver() {
        return new AcceptHeaderLocaleResolver() {
            @Override
            public Locale resolveLocale(HttpServletRequest request) {
                String acceptLanguage = request.getHeader("Accept-Language");
                if (acceptLanguage != null && acceptLanguage.contains("ko")) {
                    return Locale.KOREAN; // 한국어가 포함되어 있으면 한국어 설정
                }
                return Locale.ENGLISH; // 기본 언어: 영어
            }
        };
    }

    // 언어 변경을 위한 인터셉터 추가 (?lang=en, ?lang=ko)
    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor interceptor = new LocaleChangeInterceptor();
        interceptor.setParamName("lang"); // URL 파라미터를 통해 언어 변경 (예: ?lang=en)
        return interceptor;
    }

    // 인터셉터 등록
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localeChangeInterceptor());
    }
}
