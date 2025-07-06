package com.example.dotdot_security_test.global.exception.user;

import com.example.dotdot_security_test.global.exception.AppException;
import com.example.dotdot_security_test.global.exception.ErrorCode;

public class InvalidRefreshTokenException extends AppException {
    public InvalidRefreshTokenException(ErrorCode errorCode) {
        super(errorCode);
    }
}
