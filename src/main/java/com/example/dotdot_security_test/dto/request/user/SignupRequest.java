package com.example.dotdot_security_test.dto.request.user;

import com.example.dotdot_security_test.domain.User;
import jakarta.validation.constraints.*;
import lombok.Getter;

@Getter
public class SignupRequest {

    private static final String PASSWORD_REGEX = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,20}$";


    @NotBlank(message = "이메일은 필수입니다")
    @Email(message = "올바른 이메일 형식이어야 합니다")
    private String email;

    @NotBlank(message = "비밀번호는 필수입니다")
    @Pattern(
        regexp = PASSWORD_REGEX,
        message = "비밀번호는 영문, 숫자, 특수문자를 포함하여 8-20자리여야 합니다"
    )
    private String password;

    @NotBlank(message = "이름은 필수입니다")
    private String name;

    @NotBlank(message = "직책은 필수입니다")
    private String position;

    // 초기 회원가입용 (이메일, 비밀번호, 이름, 직책)
    public User toEntity(String password) {
        return User.builder()
                .email(email)
                .password(password)
                .name(name)
                .profileImageUrl("https://example.com/default-profile.png") // 일단 기본 프로필 이미지 URL 설정
                .position(position)
                .password(password)
                .build();
    }
}