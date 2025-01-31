package com.hoan.springformvalidation.user.service;

import com.hoan.springformvalidation.user.domain.UserDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    public void validationBadCaseTest() {
        UserDto notValidUser = new UserDto("hoan", "123");
        assertThrows(NullPointerException.class, () -> {
            userService.validationTest(notValidUser); // 테스트할 메서드
        });
    }

    @Test
    public void validationGoodCaseTest() {
        UserDto validUser = new UserDto("hoan", "123", "hoan@naver.com");
        userService.validationTest(validUser); // 테스트할 메서드

    }
}
