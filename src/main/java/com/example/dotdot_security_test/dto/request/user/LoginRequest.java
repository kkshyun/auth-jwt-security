package com.example.dotdot_security_test.dto.request.user;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class LoginRequest {
    @NotBlank(message = "이메일은 필수입니다")
    private String email;
    @NotBlank(message = "비밀번호는 필수입니다")
    private String password;
}
