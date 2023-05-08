package com.study.deposit.domain.studyRoom.dao;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import com.study.deposit.domain.studyRoom.domain.AttendanceType;
import com.study.deposit.domain.studyRoom.domain.StudyRoom;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
class StudyRoomDaoTest {
    @Autowired
    StudyRoomDao studyRoomDao;

    @Test
    @DisplayName("스터디방 최근날짜순 정렬")
    void findAllByOrderByCreateDateDesc() {
        //given
        insertExStudyRoom();
        //when
        List<StudyRoom> studyRooms = studyRoomDao.findAllByOrderByCreateDateDesc();
        //then
        assertThat(studyRooms.get(0).getTitle()).isEqualTo("first");
        assertThat(studyRooms.get(1).getTitle()).isEqualTo("second");
        assertThat(studyRooms.get(2).getTitle()).isEqualTo("third");
    }

    private void insertExStudyRoom() {
        StudyRoom secondRoom = StudyRoom.builder()
                .endDate(LocalDate.of(2100, 03, 03))
                .createDate(LocalDateTime.now().minusHours(10))
                .title("second")
                .attendanceType(AttendanceType.AttendanceCheck)
                .personCapacity(10L)
                .attendanceTime(LocalTime.now())
                .deposit(1000L)
                .build();
        StudyRoom firstRoom = StudyRoom.builder()
                .endDate(LocalDate.of(2100, 03, 03))
                .createDate(LocalDateTime.now())
                .title("first")
                .attendanceType(AttendanceType.AttendanceCheck)
                .personCapacity(10L)
                .attendanceTime(LocalTime.now())
                .deposit(1000L)
                .build();
        StudyRoom thirdRoom = StudyRoom.builder()
                .endDate(LocalDate.of(2100, 03, 03))
                .createDate(LocalDateTime.now().minusHours(24))
                .title("third")
                .attendanceType(AttendanceType.AttendanceCheck)
                .personCapacity(10L)
                .attendanceTime(LocalTime.now())
                .deposit(1000L)
                .build();
        studyRoomDao.save(firstRoom);
        studyRoomDao.save(secondRoom);
        studyRoomDao.save(thirdRoom);
    }
}