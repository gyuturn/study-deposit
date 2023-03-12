package com.study.deposit.global.common.code.auth;

import com.study.deposit.global.common.code.Code;
import lombok.Getter;

@Getter
public enum AuthErrorCode implements Code {
    GUEST_USER("EA001", "사용자가 로그인되어 있지 않음(Guest User)"),
    ;
    private String code;
    private String  message;


    AuthErrorCode(String code, String message) {
        this.code=code;
        this.message=message;
    }
}
