package com.study.deposit.global.common.code.iamPort;

import com.study.deposit.global.common.code.Code;
import lombok.Getter;

@Getter
public enum IamPortErrorCode implements Code {
    UNAUTHORIZED_TOKEN("EIP001", "IAMPORT토큰값이 유효하지 않습니다."),
            ;
    private String code;
    private String  message;


    IamPortErrorCode(String code, String message) {
        this.code=code;
        this.message=message;
    }
}