package com.example.dotdot_security_test.dto.response.user;

import com.example.dotdot_security_test.domain.User;

public record UserInfoResponse(
        String name,
        String email,
        String profileImageUrl,
        String position
) {
    public static UserInfoResponse from(User user) {
        return new UserInfoResponse(
                user.getName(),
                user.getEmail(),
                user.getProfileImageUrl(),
                user.getPosition()
        );
    }
}
