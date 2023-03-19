package com.study.deposit.global.common.code.user;

import com.study.deposit.global.common.code.Code;
import lombok.Getter;

@Getter
public enum UserErrorCode  implements Code {
    DUP_NICKNAME("EU001", "중복된 닉네임입니다."),;
    private String code;
    private String  message;


    UserErrorCode(String code, String message) {
        this.code=code;
        this.message=message;
    }
}