package com.study.deposit.domain.studyRoom.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.study.deposit.domain.user.domain.LoginType;
import com.study.deposit.domain.user.domain.Role;
import com.study.deposit.domain.user.domain.Users;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class UserStudyRoomTest {
    @Test
    @DisplayName("스터디방, 유저 -> UserStudyRoom entity 생성(방장용)")
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
        Users testUser = Users.builder()
                .id(UUID.randomUUID())
                .role(Role.USER)
                .email("test@naver.com")
                .loginType(LoginType.KAKAO)
                .nickName("testNickName")
                .build();
        //when
        UserStudyRoom userStudyRoom = UserStudyRoom.toEntityForHost(testStudyRoom, testUser);
        //then
        assertThat(userStudyRoom.getUsers()).isEqualTo(testUser);
        assertThat(userStudyRoom.getStudyRoom()).isEqualTo(testStudyRoom);
        assertThat(userStudyRoom.getStudyRoomRole()).isEqualTo(StudyRoomRole.HOST);
    }
}