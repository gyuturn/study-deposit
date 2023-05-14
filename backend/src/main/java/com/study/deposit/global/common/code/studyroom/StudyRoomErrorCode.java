package com.study.deposit.global.common.code.studyroom;

import com.study.deposit.global.common.code.Code;
import lombok.Getter;

@Getter
public enum StudyRoomErrorCode implements Code {
    NOT_ENOUGH_POINT_FOR_DEPOSIT("STD001", "스터디방에 입장하기 위한 포인트가 부족합니다.(보증금보다 작은 포인트 보유)"),
    ALREADY_ENTER_ROOM("STD002", "이미 입장한 스터디방입니다."),
    NOT_EXIST("STD003", "존재하지 않은 스터디방입니다.");
    private String code;
    private String message;


    StudyRoomErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }


}

