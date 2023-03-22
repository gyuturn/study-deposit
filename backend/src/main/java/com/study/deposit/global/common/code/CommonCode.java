package com.study.deposit.global.common.code;

import lombok.Getter;

@Getter
public enum CommonCode implements Code{
    OK("C001", "정상 처리"),
    CREATED("C002","새로운 리소스 생성"),
    BAD_REQUEST("C003","잘못된 요청"),
    UNAUTHORIZED("C004","인증되지 않은 요청"),
    FORBIDDEN("C005","접근 권한 부족"),
    NOT_FOUND("C006","요청 리소스 존재하지 않음"),
    INTERNAL_SERVER_ERROR("C007","서버에 오류 발생"),
    REDIRECT("C008","리다이렉트 필요함");


    private String code;
    private String  message;

    CommonCode(String code, String message) {
        this.code=code;
        this.message=message;
    }


}
