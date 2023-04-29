package com.study.deposit.global.common.code.studyroom;

import com.study.deposit.global.common.code.Code;
import lombok.Getter;

@Getter
public enum StudyRoomErrorCode implements Code {
    NOT_ENOUGH_POINT_FOR_DEPOSIT("STD001", "스터디방에 입장하기 위한 포인트가 부족합니다.(보증금보다 작은 포인트 보유)");
    private String code;
    private String message;


    StudyRoomErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }


}

