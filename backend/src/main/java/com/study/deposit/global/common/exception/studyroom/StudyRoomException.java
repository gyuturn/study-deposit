package com.study.deposit.global.common.exception.studyroom;

import com.study.deposit.global.common.code.Code;
import com.study.deposit.global.common.exception.DefaultException;
import org.springframework.http.HttpStatus;

public class StudyRoomException extends DefaultException {


    public StudyRoomException(Code code, HttpStatus httpStatus) {
        super(code, httpStatus);
    }
}
