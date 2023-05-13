package com.study.deposit.domain.attendance.dto;

import java.time.LocalDateTime;
import java.time.LocalTime;
import lombok.Data;

@Data
public class PostAttendanceReqDto {
    private Long studyRoomId;
    private LocalDateTime reqTime;
}
