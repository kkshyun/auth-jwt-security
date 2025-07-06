package com.example.dotdot_security_test.global.security;

import com.example.dotdot_security_test.domain.User;
import com.example.dotdot_security_test.global.exception.user.UserNotFoundException;
import com.example.dotdot_security_test.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static com.example.dotdot_security_test.global.exception.user.UserErrorCode.NOT_FOUND;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(NOT_FOUND));

        return new CustomUserDetails(user);
    }

    public CustomUserDetails loadUserById(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(NOT_FOUND));
        return new CustomUserDetails(user);
    }
}
