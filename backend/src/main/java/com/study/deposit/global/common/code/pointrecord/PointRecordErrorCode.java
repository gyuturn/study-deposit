package com.study.deposit.global.common.code.pointrecord;

import com.study.deposit.global.common.code.Code;
import lombok.Getter;

@Getter
public enum PointRecordErrorCode implements Code {
    NOT_EXIST_POINT_RECORD("EPR001", "존재하지 않은 pointrecord 데이터 입니다."),
    NOT_VALID_PAYMENT("EPR002", "유효하지 않은 결제입니다.(데이터 조작 의심)"),
            ;
    private String code;
    private String  message;


    PointRecordErrorCode(String code, String message) {
        this.code=code;
        this.message=message;
    }
}
