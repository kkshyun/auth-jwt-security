package com.example.dotdot_security_test.global.exception.user;

import com.example.dotdot_security_test.global.exception.AppException;
import com.example.dotdot_security_test.global.exception.ErrorCode;

public class EmailAlreadyExistsException extends AppException {
    public EmailAlreadyExistsException(ErrorCode errorCode) {
        super(errorCode);
    }
}
