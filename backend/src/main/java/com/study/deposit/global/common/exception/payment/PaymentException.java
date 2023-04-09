package com.study.deposit.global.common.exception.payment;

import com.study.deposit.global.common.code.Code;
import com.study.deposit.global.common.exception.DefaultException;
import org.springframework.http.HttpStatus;

public class PaymentException extends DefaultException {


    public PaymentException(Code code, HttpStatus httpStatus) {
        super(code, httpStatus);
    }
}
