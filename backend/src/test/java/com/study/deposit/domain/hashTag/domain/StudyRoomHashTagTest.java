package com.study.deposit.domain.hashTag.domain;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import com.study.deposit.domain.studyRoom.domain.AttendanceType;
import com.study.deposit.domain.studyRoom.domain.StudyRoom;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class StudyRoomHashTagTest {

    @Test
    @DisplayName("스터디방, 해시태크 -> StudyRoomHashTag entity 생성")
    void toEntity() {
        //given
        StudyRoom testStudyRoom = StudyRoom.builder().
                id(1L)
                .createDate(LocalDateTime.now())
                .attendanceTime(LocalTime.of(12, 0, 0))
                .personCapacity(10L)
                .attendanceType(AttendanceType.AttendanceCheck)
                .endDate(LocalDate.of(2022, 02, 01))
                .title("test")
                .deposit(1000L)
                .build();
        HashTag testTag = HashTag.builder()
                .id(2L)
                .tagName("testTag")
                .build();
        //when
        StudyRoomHashTag studyRoomHashTag = StudyRoomHashTag.toEntity(testStudyRoom, testTag);
        //then
        assertThat(studyRoomHashTag.getHashTag()).isEqualTo(testTag);
        assertThat(studyRoomHashTag.getStudyRoom()).isEqualTo(testStudyRoom);
    }
}