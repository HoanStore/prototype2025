package com.hoan.springformvalidation.user.domain;


import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@NoArgsConstructor
@Setter
@Getter
@ToString
public class UserDto {

    @NotEmpty(message = "Username is required")
    private String username;

    @NotEmpty(message = "Password is required")
    private String password;

    @NotEmpty(message = "Email is required")
    private String email;

    public UserDto(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public UserDto(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
