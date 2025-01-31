package com.hoan.springformvalidation.user.service;

import com.hoan.springformvalidation.user.domain.UserDto;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class UserService {

    public void validationTest(@Validated UserDto userDto) {
        System.out.println("userDto = " + userDto.toString());
    }
}
