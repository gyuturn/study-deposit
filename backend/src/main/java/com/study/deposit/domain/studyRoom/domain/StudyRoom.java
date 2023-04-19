package com.study.deposit.domain.studyRoom.domain;

import com.sun.istack.NotNull;
import java.time.LocalDateTime;
import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
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
    private LocalDateTime finishDate;

    @NotNull
    private String title;

    @NotNull
    @Enumerated(EnumType.STRING)
    private AttendanceType attendanceType;
    @NotNull
    private String attendanceTime;

    @NotNull
    private Long deposit;


}
