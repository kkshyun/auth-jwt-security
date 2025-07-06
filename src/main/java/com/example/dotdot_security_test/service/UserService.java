package com.example.dotdot_security_test.service;

import com.example.dotdot_security_test.domain.User;
import com.example.dotdot_security_test.dto.request.user.PasswordChangeRequest;
import com.example.dotdot_security_test.dto.request.user.UserUpdateRequest;
import com.example.dotdot_security_test.dto.response.user.UserInfoResponse;
import com.example.dotdot_security_test.global.exception.user.EmailAlreadyExistsException;
import com.example.dotdot_security_test.global.exception.user.InvalidPasswordException;
import com.example.dotdot_security_test.global.exception.user.UserNotFoundException;
import com.example.dotdot_security_test.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static com.example.dotdot_security_test.global.exception.user.UserErrorCode.*;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserInfoResponse getUserInfo(Long userId) {
        return UserInfoResponse.from(findUserById(userId));
    }


    public UserInfoResponse updateUserInfo(Long userId, UserUpdateRequest request) {
        User user = findUserById(userId);
        // 이메일이 변경되었는지 확인
        if (!user.getEmail().equals(request.getEmail())) {
            // 이메일이 변경되었다면, 이미 존재하는 이메일인지 확인
            if (userRepository.existsByEmail(request.getEmail())) {
                throw new EmailAlreadyExistsException(EMAIL_ALREADY_EXISTS);
            }
        }
        // 이메일이 변경되었다면, 이메일 업데이트
        user.updateUserInfo(request);
        return UserInfoResponse.from(user);
    }

    public UserInfoResponse updateProfileImage(Long userId, String image) {
        User user = findUserById(userId);
        user.updateProfileImageUrl(image);
        return UserInfoResponse.from(user);
    }

    public void updatePassword(Long userId, PasswordChangeRequest request){
        User user = findUserById(userId);
        // 암호화된 저장된 비밀번호와 비밀번호 비교
        if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
            throw new InvalidPasswordException(INVALID_PASSWORD);
        }
        user.updatePassword(passwordEncoder.encode(request.getNewPassword()));
    }

    private User findUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(NOT_FOUND));
    }
}
