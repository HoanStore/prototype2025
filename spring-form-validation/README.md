1. 의존성 추가

```
<dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-validation</artifactId>
            <version>3.4.2</version>
</dependency>
```


2. DTO 또는 VO에 유효성 검사 애노테이션 추가

```
@Data
public class UserDto {

    @NotEmpty(message = "Username is required")
    private String username;

    @NotEmpty(message = "Password is required")
    private String password;

    @NotEmpty(message = "Email is required")
    private String email;
}
```


3. 유효성 검사가 필요한 곳에 @Validated 어노테이션 추가
``` Controller
    @PostMapping("/register")
    public String register(@RequestBody @Validated UserDto userDto) {
        System.out.println("userDto = " + userDto);
        return "User signed up successfully";
    }
```

``` Service
public void validationTest(@Validated UserDto userDto) {
        System.out.println("userDto = " + userDto.toString());
    }
```

