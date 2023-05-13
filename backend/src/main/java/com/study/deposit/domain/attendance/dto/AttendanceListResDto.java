package com.study.deposit.domain.attendance.dto;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AttendanceListResDto {
    @NotNull
    private LocalTime startAttendanceTime;
    @NotNull
    private LocalTime endAttendanceTime;
    @NotNull
    private List<AttendanceInfo> attendanceInfo = new ArrayList<>();

}
