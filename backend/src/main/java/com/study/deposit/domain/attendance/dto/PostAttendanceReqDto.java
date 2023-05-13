package com.study.deposit.domain.attendance.dto;

import java.time.LocalDateTime;
import java.time.LocalTime;
import javax.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

@Data
public class PostAttendanceReqDto {
    @NotNull
    private Long studyRoomId;
    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss ")
    private LocalDateTime reqTime;
}
