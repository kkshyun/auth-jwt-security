package com.example.dotdot_security_test.global.exception.user;

import com.example.dotdot_security_test.global.exception.AppException;
import com.example.dotdot_security_test.global.exception.ErrorCode;

public class InvalidCredentialsException extends AppException {
    public InvalidCredentialsException(ErrorCode errorCode) {
        super(errorCode);
    }
}
