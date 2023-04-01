package com.study.deposit.global.common.code.pointrecord;

import com.study.deposit.global.common.code.Code;
import lombok.Getter;

@Getter
public enum PointRecordErrorCode implements Code {
    NOT_EXIST_POINT_RECORD("EPR001", "존재하지 않은 pointrecord 데이터 입니다."),
            ;
    private String code;
    private String  message;


    PointRecordErrorCode(String code, String message) {
        this.code=code;
        this.message=message;
    }
}
