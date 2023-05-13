package com.study.deposit.domain.attendance.dto;

import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AttendanceInfo {
    @NotNull
    private String usersNickName;
    @NotNull
    private Long totalAttendanceDay;
    @NotNull
    private Long AbsenceDay;
    @NotNull
    private Boolean todaysAttendance;
}
