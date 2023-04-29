package com.study.deposit.domain.studyRoom.domain;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import com.study.deposit.domain.studyRoom.dto.StudyRoomMakingReqDto;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class StudyRoomTest {

    @Test
    @DisplayName("방생성 dto -> entity")
    void makeEntity() {
        //given
        StudyRoomMakingReqDto dto = new StudyRoomMakingReqDto();
        dto.setPersonCapacity(10L);
        dto.setAttendanceType(AttendanceType.AttendanceCheck);
        dto.setAttendanceTime(LocalTime.of(12, 30, 00));
        dto.setDeposit(1000L);
        dto.setTitle("테스트방");
        dto.setEndDate(LocalDate.of(2023, 12, 01));

        //when
        StudyRoom result = StudyRoom.toEntity(dto);
        //then
        assertThat(result.getTitle()).isEqualTo(dto.getTitle());
        assertThat(result.getDeposit()).isEqualTo(dto.getDeposit());
        assertThat(result.getAttendanceTime()).isEqualTo(dto.getAttendanceTime());
        assertThat(result.getEndDate()).isEqualTo(dto.getEndDate());
        assertThat(result.getPersonCapacity()).isEqualTo(dto.getPersonCapacity());
        assertThat(result.getAttendanceType()).isEqualTo(dto.getAttendanceType());

    }

}