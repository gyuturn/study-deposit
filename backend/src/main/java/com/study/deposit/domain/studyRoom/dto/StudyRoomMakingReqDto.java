package com.study.deposit.domain.studyRoom.dto;

import com.study.deposit.domain.hashTag.domain.HashTag;
import com.study.deposit.domain.studyRoom.domain.AttendanceType;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

@Data
public class StudyRoomMakingReqDto {
    @NotNull
    private String title;
    @NotNull
    private AttendanceType attendanceType;
    @NotNull
    @DateTimeFormat(pattern = "HH:mm:ss")
    private LocalTime attendanceTime;
    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;
    @NotNull
    @Positive
    private Long personCapacity;
    @NotNull
    @Positive
    private Long deposit;

    private List<HashTag> hashTags;



}
