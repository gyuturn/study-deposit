package com.study.deposit.domain.studyRoom.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.study.deposit.domain.hashTag.domain.HashTag;
import com.study.deposit.domain.hashTag.service.HashTagService;
import com.study.deposit.domain.point.dao.PointRecordDao;
import com.study.deposit.domain.point.domain.PaymentType;
import com.study.deposit.domain.point.service.PointRecordService;
import com.study.deposit.domain.studyRoom.dao.StudyRoomDao;
import com.study.deposit.domain.studyRoom.dao.UserStudyRoomDao;
import com.study.deposit.domain.studyRoom.domain.AttendanceType;
import com.study.deposit.domain.studyRoom.domain.StudyRoom;
import com.study.deposit.domain.studyRoom.domain.UserStudyRoom;
import com.study.deposit.domain.studyRoom.dto.StudyRoomInfoResDto;
import com.study.deposit.domain.studyRoom.dto.StudyRoomMakingReqDto;
import com.study.deposit.domain.user.domain.LoginType;
import com.study.deposit.domain.user.domain.Role;
import com.study.deposit.domain.user.domain.Users;
import com.study.deposit.domain.user.service.AuthService;
import com.study.deposit.global.common.exception.payment.PaymentException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class StudyRoomServiceTest {
    @Mock
    StudyRoomDao studyRoomDao;
    @Mock
    UserStudyRoomDao userStudyRoomDao;
    @Mock
    AuthService authService;
    @Mock
    HashTagService hashTagService;
    @Mock
    PointRecordService pointRecordService;

    @InjectMocks
    StudyRoomService studyRoomService;

    @Test
    @DisplayName("스터디방 생성 로직")
    void makeStudyRoom() {
        //given
        StudyRoomMakingReqDto dto = createRoomReqDto();
        Users hostUser = makeTestHostUser();
        when(authService.getUser()).thenReturn(hostUser);

        StudyRoom newStudyRoom = StudyRoom.builder()
                .id(1L)
                .title(dto.getTitle())
                .attendanceType(dto.getAttendanceType())
                .personCapacity(dto.getPersonCapacity())
                .attendanceTime(dto.getAttendanceTime())
                .createDate(LocalDateTime.now())
                .deposit(dto.getDeposit())
                .endDate(dto.getEndDate())
                .build();
        when(studyRoomDao.save(any())).thenReturn(newStudyRoom);

        //when
        studyRoomService.makeStudyRoom(dto);

        // Assert
        verify(studyRoomDao).save(any(StudyRoom.class));
        verify(hashTagService).hashTagsMapStudyRoom(dto.getHashTags(), newStudyRoom);
        verify(userStudyRoomDao).save(any(UserStudyRoom.class));
    }

    private Users makeTestHostUser() {
        return Users.builder()
                .id(UUID.randomUUID())
                .role(Role.USER)
                .email("test@naver.com")
                .loginType(LoginType.KAKAO)
                .nickName("testNickName")
                .build();
    }

    private StudyRoomMakingReqDto createRoomReqDto() {
        StudyRoomMakingReqDto dto = new StudyRoomMakingReqDto();
        dto.setPersonCapacity(10L);
        dto.setAttendanceType(AttendanceType.AttendanceCheck);
        dto.setAttendanceTime(LocalTime.of(12, 30, 00));
        dto.setDeposit(1000L);
        dto.setTitle("테스트방");
        dto.setHashTags(Arrays.asList(new HashTag(1L, "tag1"), new HashTag(1L, "tag1")));
        dto.setEndDate(LocalDate.of(2023, 12, 01));
        return dto;
    }

    @Test
    @DisplayName("스터디방 입장 로직-실패 케이스")
    public void testEnterStudyRoom_InsufficientFunds() {
        Users hostUser = makeTestHostUser();

        when(authService.getUser()).thenReturn(hostUser);
        when(pointRecordService.getSumRecordByUser(hostUser)).thenReturn(1000L);

        Long deposit = 5000L;

        // Ensure that the PaymentException is thrown with the expected error code and status
        Assertions.assertThrows(
                PaymentException.class,
                () -> {
                    studyRoomService.enterStudyRoom(deposit);
                }
        );
        verify(pointRecordService, never()).insertRecord(hostUser, deposit, PaymentType.PURCHASE);
    }

    @Test
    @DisplayName("스터디방 입장 로직-성공 케이스")
    public void testEnterStudyRoom_Valid() {
        Users hostUser = makeTestHostUser();

        when(authService.getUser()).thenReturn(hostUser);
        when(pointRecordService.getSumRecordByUser(hostUser)).thenReturn(1000L);

        Long deposit = 500L;
        studyRoomService.enterStudyRoom(deposit);

        // Verify that the insertRecord method was called with the correct arguments
        verify(pointRecordService, times(1)).insertRecord(hostUser, deposit, PaymentType.PURCHASE);
    }

    @Test
    @DisplayName("스터디방 전체 조회")
    void getStudyRoomList() {
        // Create sample data
        StudyRoom studyRoom1 = StudyRoom.builder()
                .id(1L)
                .deposit(1000L)
                .attendanceTime(LocalTime.of(13, 0, 0))
                .personCapacity(10L)
                .attendanceType(AttendanceType.AttendanceCheck)
                .title("testRoom1")
                .createDate(LocalDateTime.now())
                .endDate(LocalDate.of(2100, 12, 03))
                .build();

        StudyRoom studyRoom2 = StudyRoom.builder()
                .id(1L)
                .deposit(1000L)
                .attendanceTime(LocalTime.of(13, 0, 0))
                .personCapacity(10L)
                .attendanceType(AttendanceType.AttendanceCheck)
                .title("testRoom2")
                .createDate(LocalDateTime.now())
                .endDate(LocalDate.of(2100, 12, 03))
                .build();

        List<StudyRoom> studyRooms = new ArrayList<>();
        studyRooms.add(studyRoom1);
        studyRooms.add(studyRoom2);

        List<HashTag> hashTags1 = new ArrayList<>();
        hashTags1.add(HashTag.builder()
                .id(11L)
                .tagName("tag1").build());
        hashTags1.add(HashTag.builder()
                .id(11L)
                .tagName("tag1").build());

        List<HashTag> hashTags2 = new ArrayList<>();
        hashTags2.add(HashTag.builder()
                .id(21L)
                .tagName("tag11").build());
        hashTags2.add(HashTag.builder()
                .id(22L)
                .tagName("tag12").build());

        when(studyRoomDao.findAllByOrderByCreateDateDesc()).thenReturn(studyRooms);
        when(hashTagService.getHashTagsByStudyRoom(studyRoom1)).thenReturn(hashTags1);
        when(hashTagService.getHashTagsByStudyRoom(studyRoom2)).thenReturn(hashTags2);

        // Call the method under test
        List<StudyRoomInfoResDto> result = studyRoomService.getStudyRoomList();

        // Verify the expected behavior
        verify(studyRoomDao).findAllByOrderByCreateDateDesc();
        verify(hashTagService).getHashTagsByStudyRoom(studyRoom1);
        verify(hashTagService).getHashTagsByStudyRoom(studyRoom2);
        assertEquals(2, result.size()); // Assert the size of the returned list
        // Assert the properties of the first StudyRoomInfoResDto
        assertEquals("testRoom1", result.get(0).getTitle());
        // Assert the properties of the second StudyRoomInfoResDto
        assertEquals("testRoom2", result.get(1).getTitle());
    }

}