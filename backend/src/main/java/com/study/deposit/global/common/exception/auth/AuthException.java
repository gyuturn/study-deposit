package com.study.deposit.global.common.exception.auth;

import com.study.deposit.global.common.code.Code;
import com.study.deposit.global.common.exception.DefaultException;
import org.springframework.http.HttpStatus;

public class AuthException extends DefaultException {


    public AuthException(Code code, HttpStatus httpStatus) {
        super(code, httpStatus);
    }
}
