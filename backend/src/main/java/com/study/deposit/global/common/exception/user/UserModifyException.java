package com.study.deposit.global.common.exception.user;

import com.study.deposit.global.common.code.Code;
import com.study.deposit.global.common.exception.DefaultException;
import org.springframework.http.HttpStatus;

public class UserModifyException extends DefaultException {


    public UserModifyException(Code code, HttpStatus httpStatus) {
        super(code, httpStatus);
    }
}