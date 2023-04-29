package com.study.deposit.domain.studyRoom.domain;

public enum AttendanceType {
    AttendanceCheck("출석체크");

    private String detail;

    AttendanceType(String detail) {
        this.detail = detail;
    }
}
