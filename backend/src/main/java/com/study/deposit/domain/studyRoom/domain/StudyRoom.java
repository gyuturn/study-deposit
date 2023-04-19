package com.study.deposit.domain.studyRoom.domain;

import com.study.deposit.domain.studyRoom.dto.StudyRoomMakingReqDto;
import com.sun.istack.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StudyRoom {
    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    private LocalDateTime createDate;
    @NotNull
    private LocalDate endDate;

    @NotNull
    private String title;

    @NotNull
    @Enumerated(EnumType.STRING)
    private AttendanceType attendanceType;
    @NotNull
    private LocalTime attendanceTime;

    @NotNull
    @Positive
    private Long deposit;

    @NotNull
    @Positive
    private Long personCapacity;

    public static StudyRoom toEntity(StudyRoomMakingReqDto dto) {
        return StudyRoom.builder()
                .endDate(dto.getEndDate())
                .createDate(LocalDateTime.now())
                .attendanceType(dto.getAttendanceType())
                .attendanceTime(dto.getAttendanceTime())
                .title(dto.getTitle())
                .deposit(dto.getDeposit())
                .personCapacity(dto.getPersonCapacity())
                .build();
    }

}
