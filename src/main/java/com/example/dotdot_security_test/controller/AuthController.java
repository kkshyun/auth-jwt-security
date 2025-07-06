package com.example.dotdot_security_test.controller;

import com.example.dotdot_security_test.dto.request.user.EmailCheckRequest;
import com.example.dotdot_security_test.dto.request.user.LoginRequest;
import com.example.dotdot_security_test.dto.request.user.RefreshTokenRequest;
import com.example.dotdot_security_test.dto.request.user.SignupRequest;
import com.example.dotdot_security_test.dto.response.user.TokenResponse;
import com.example.dotdot_security_test.global.dto.DataResponse;
import com.example.dotdot_security_test.global.exception.user.InvalidRefreshTokenException;
import com.example.dotdot_security_test.global.security.CustomUserDetails;
import com.example.dotdot_security_test.service.AuthService;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.example.dotdot_security_test.global.exception.user.UserErrorCode.INVALID_REFRESH_TOKEN;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController implements AuthControllerSpecification {

    private final AuthService authService;


    @PostMapping("/check-email")
    public ResponseEntity<DataResponse<Boolean>> checkEmailDuplicate(
            @Valid @RequestBody EmailCheckRequest request) {
        boolean isDuplicate = authService.isEmailDuplicate(request.getEmail());
        return ResponseEntity.ok(DataResponse.from(isDuplicate));
    }


    @PostMapping("/signup")
    public ResponseEntity<DataResponse<String>> signup(
            @Parameter(description = "회원가입 정보", required = true) 
            @Valid @RequestBody SignupRequest request) {
        authService.signup(request);
        return ResponseEntity.ok(DataResponse.from("회원가입 성공"));
    }


    @PostMapping("/login")
    public ResponseEntity<DataResponse<TokenResponse>> login(
            @Parameter(description = "로그인 정보", required = true)
            @Valid @RequestBody LoginRequest request) {
        TokenResponse token = authService.login(request);
        return ResponseEntity.ok(DataResponse.from(token));
    }


    @PostMapping("/reissue")
    public ResponseEntity<DataResponse<String>> reissue(
            @Parameter(description = "Refresh 토큰", required = true)
            @Valid @RequestBody RefreshTokenRequest request) {
        String refreshToken = request.getRefreshToken();

        if (refreshToken == null || refreshToken.isBlank()) {
            throw new InvalidRefreshTokenException(INVALID_REFRESH_TOKEN);
        }

        String newAccessToken = authService.reissue(refreshToken);
        return ResponseEntity.ok(DataResponse.from(newAccessToken));
    }


    @PostMapping("/logout")
    public ResponseEntity<DataResponse<String>> logout(
            @Parameter(hidden = true)
            @AuthenticationPrincipal CustomUserDetails userDetails) {

        Long userId = userDetails.getId();
        authService.logout(userId);
        return ResponseEntity.ok(DataResponse.from("로그아웃 완료"));
    }
}