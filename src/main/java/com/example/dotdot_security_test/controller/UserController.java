package com.example.dotdot_security_test.controller;

import com.example.dotdot_security_test.dto.request.user.PasswordChangeRequest;
import com.example.dotdot_security_test.dto.request.user.UserUpdateRequest;
import com.example.dotdot_security_test.dto.response.user.UserInfoResponse;
import com.example.dotdot_security_test.global.dto.DataResponse;
import com.example.dotdot_security_test.global.security.CustomUserDetails;
import com.example.dotdot_security_test.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/users")
@RequiredArgsConstructor
@Tag(name = "UserController", description = "User 관련 API")
public class UserController implements UserControllerSpecification{
    private final UserService userService;


    @GetMapping("/me")
    public ResponseEntity<DataResponse<UserInfoResponse>> getMyInfo(@AuthenticationPrincipal CustomUserDetails userDetails) {
        Long userId = userDetails.getId();
        UserInfoResponse userInfo = userService.getUserInfo(userId);
        return ResponseEntity.ok(DataResponse.from(userInfo));
    }


    @PutMapping("/me")
    public ResponseEntity<DataResponse<UserInfoResponse>> updateMyInfo(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @Valid @RequestBody UserUpdateRequest request) {
        Long userId = userDetails.getId();
        UserInfoResponse updatedUserInfo = userService.updateUserInfo(userId, request);
        return ResponseEntity.ok(DataResponse.from(updatedUserInfo));
    }



    @PutMapping("/me/profile-image")
    public ResponseEntity<DataResponse<UserInfoResponse>> updateProfileImage(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestParam("image") String image) {
        Long userId = userDetails.getId();
        UserInfoResponse updatedUserInfo = userService.updateProfileImage(userId, image);
        return ResponseEntity.ok(DataResponse.from(updatedUserInfo));
    }



    @PutMapping("/me/password")
    public ResponseEntity<DataResponse<Void>> updatePassword(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @Valid @RequestBody PasswordChangeRequest request) {
        Long userId = userDetails.getId();
        userService.updatePassword(userId, request);
        return ResponseEntity.ok(DataResponse.ok());
    }
}
