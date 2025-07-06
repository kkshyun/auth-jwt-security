package com.example.dotdot_security_test.controller;

import com.example.dotdot_security_test.dto.request.user.EmailCheckRequest;
import com.example.dotdot_security_test.dto.request.user.LoginRequest;
import com.example.dotdot_security_test.dto.request.user.RefreshTokenRequest;
import com.example.dotdot_security_test.dto.request.user.SignupRequest;
import com.example.dotdot_security_test.dto.response.user.TokenResponse;
import com.example.dotdot_security_test.global.dto.DataResponse;
import com.example.dotdot_security_test.global.dto.ErrorResponse;
import com.example.dotdot_security_test.global.security.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "AuthController", description = "Auth 관련 API")
public interface AuthControllerSpecification {

    @Operation(summary = "이메일 중복 확인", description = "이메일 중복 여부를 확인합니다. 사용 가능하면 false, 중복되면 true를 반환합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "중복 여부 반환 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 입력값 (COMMON-006)",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
    })
    @PostMapping("/check-email")
    ResponseEntity<DataResponse<Boolean>> checkEmailDuplicate
            ( @Valid @RequestBody EmailCheckRequest request);


    @Operation(summary = "회원가입", description = "새로운 사용자를 등록합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "회원가입 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 입력값 (COMMON-006)",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "400", description = "이미 존재하는 이메일 (USER-002)",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping("/signup")
    ResponseEntity<DataResponse<String>> signup(
            @Parameter(description = "회원가입 정보", required = true)
            @Valid @RequestBody SignupRequest request);


    @Operation(summary = "로그인", description = "사용자 인증 후 JWT 토큰을 발급합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "로그인 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 입력값 (COMMON-006)",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = "이메일 또는 비밀번호 불일치 (USER-004)",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 회원 (USER-001)",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "서버 내부 오류 (COMMON-005)",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping("/login")
    ResponseEntity<DataResponse<TokenResponse>> login(
            @Parameter(description = "로그인 정보", required = true)
            @Valid @RequestBody LoginRequest request);


    @Operation(summary = "토큰 갱신", description = "Refresh 토큰을 사용하여 새로운 Access 토큰을 발급받습니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "토큰 재발급 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 입력값 (COMMON-006)",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = "유효하지 않은 리프레시 토큰 (USER-003)",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 회원 (USER-001)",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping("/reissue")
    ResponseEntity<DataResponse<String>> reissue(
            @Parameter(description = "Refresh 토큰", required = true)
            @Valid @RequestBody RefreshTokenRequest request);



    @Operation(summary = "로그아웃", description = "사용자를 로그아웃 처리합니다.")
    @SecurityRequirement(name = "bearerAuth")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "로그아웃 성공"),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 사용자 (USER-001)",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping("/logout")
    ResponseEntity<DataResponse<String>> logout(
            @Parameter(hidden = true)
            @AuthenticationPrincipal CustomUserDetails userDetails);


}
