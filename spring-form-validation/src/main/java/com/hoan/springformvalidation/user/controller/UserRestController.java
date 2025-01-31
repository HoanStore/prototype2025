package com.hoan.springformvalidation.user.controller;

import com.hoan.springformvalidation.user.domain.UserDto;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserRestController {

    @PostMapping("/register")
    public String register(@RequestBody @Validated UserDto userDto) {
        System.out.println("userDto = " + userDto);
        return "User signed up successfully";
    }




}
