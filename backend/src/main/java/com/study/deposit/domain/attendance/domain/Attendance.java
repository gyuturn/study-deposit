package com.study.deposit.domain.attendance.domain;

import com.study.deposit.domain.hashTag.domain.HashTag;
import com.study.deposit.domain.studyRoom.domain.AttendanceType;
import com.study.deposit.domain.studyRoom.domain.StudyRoom;
import com.study.deposit.domain.user.domain.Users;
import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Attendance {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "study_room_id")
    @NotNull
    private StudyRoom studyRoom;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "users_id")
    @NotNull
    private Users users;

    @NotNull
    @Enumerated(EnumType.STRING)
    private AttendanceState attendanceState;

    @NotNull
    private LocalDateTime attendanceTime;

    public void updateAttendanceState(AttendanceState attendanceState) {
        this.attendanceState = attendanceState;
    }



}
