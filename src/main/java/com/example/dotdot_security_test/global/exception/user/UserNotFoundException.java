package com.example.dotdot_security_test.global.exception.user;

import com.example.dotdot_security_test.global.exception.AppException;
import com.example.dotdot_security_test.global.exception.ErrorCode;

public class UserNotFoundException extends AppException {
    public UserNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }
}
