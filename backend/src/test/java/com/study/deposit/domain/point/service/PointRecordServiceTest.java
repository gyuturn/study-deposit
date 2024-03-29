package com.study.deposit.domain.point.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.study.deposit.domain.point.dao.PointRecordDao;
import com.study.deposit.domain.point.domain.PaymentType;
import com.study.deposit.domain.point.domain.PointRecord;
import com.study.deposit.domain.point.dto.PointRecordPrepareDto;
import com.study.deposit.domain.user.domain.LoginType;
import com.study.deposit.domain.user.domain.Role;
import com.study.deposit.domain.user.domain.Users;
import com.study.deposit.domain.user.service.AuthService;
import com.study.deposit.global.common.exception.DefaultException;
import com.study.deposit.global.common.exception.payment.PaymentException;
import com.study.deposit.global.config.annotation.WithAuthUser;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PointRecordServiceTest {
    @Mock
    PointRecordDao pointRecordDao;

    @Mock
    AuthService authService;

    @InjectMocks
    PointRecordService pointRecordService;


    @Test
    @DisplayName("포인트 총합계산 로직")
    public void testSumRecordByUser() {
        //given
        Users users = makeUser();

        List<PointRecord> pointRecords = makePointRecords(users);
        when(pointRecordDao.findByUsers(users)).thenReturn(pointRecords);
        //when
        Long sum = pointRecordService.getSumRecordByUser(users);

        //then
        assertEquals(600L, sum);
    }

    private List<PointRecord> makePointRecords(Users users) {
        List<PointRecord> testRecords = new ArrayList<>();
        testRecords.add(
                new PointRecord(UUID.randomUUID().toString(), users, LocalDateTime.now(), PaymentType.CHARGE, 100L));
        testRecords.add(
                new PointRecord(UUID.randomUUID().toString(), users, LocalDateTime.now(), PaymentType.CHARGE, 200L));
        testRecords.add(
                new PointRecord(UUID.randomUUID().toString(), users, LocalDateTime.now(), PaymentType.CHARGE, 300L));
        return testRecords;
    }

    private Users makeUser() {
        return Users.builder()
                .nickName("test")
                .loginType(LoginType.KAKAO)
                .email("test@naver.com")
                .role(Role.USER)
                .id(UUID.randomUUID()).build();
    }

    @Test
    @DisplayName("포인트 삽입 로직")
    void insertRecord() {
        //given
        Users testUser = makeUser();
        Long testChargeAmount = 100L;
        PointRecordPrepareDto prepareDto = new PointRecordPrepareDto();
        prepareDto.setMerchant_uid(UUID.randomUUID().toString());
        prepareDto.setAmount(testChargeAmount);

        //when
        pointRecordService.insertRecord(testUser, prepareDto, PaymentType.CHARGE);

        //then
        verify(pointRecordDao).save(any(PointRecord.class));
    }

    @Test
    @DisplayName("포인트 차감 로직")
    void insertRecord_DELETE() {
        //given
        Users testUser = makeUser();
        Long testChargeAmount = -100L;
        PointRecordPrepareDto prepareDto = new PointRecordPrepareDto();
        prepareDto.setMerchant_uid(UUID.randomUUID().toString());
        prepareDto.setAmount(testChargeAmount);

        //when
        pointRecordService.insertRecord(testUser, prepareDto, PaymentType.DEPOSIT);

        //then
        verify(pointRecordDao).save(any(PointRecord.class));
    }

    @Test
    @DisplayName("merchant_uid(pk)로 찾기[성공]")
    void findByMerchantId_shouldReturnPointRecord() {
        // Given
        String merchantUuid = "12345";
        PointRecord pointRecord = PointRecord.builder()
                .merchant_uid(merchantUuid)
                .chargeAmount(1000L)
                .paymentDate(LocalDateTime.now())
                .users(makeUser())
                .paymentType(PaymentType.CHARGE)
                .build();
        when(pointRecordDao.findById(merchantUuid)).thenReturn(Optional.of(pointRecord));

        // When
        PointRecord result = pointRecordService.findByMerchantId(merchantUuid);

        // Then
        assertEquals(pointRecord, result);
    }

    @Test
    @DisplayName("merchant_uid(pk)로 찾기[실패]")
    void findByMerchantId_shouldThrowExceptionIfPointRecordNotFound() {
        // Given
        String merchantUuid = "12345";
        when(pointRecordDao.findById(merchantUuid)).thenReturn(Optional.empty());

        // When / Then
        assertThrows(DefaultException.class, () -> pointRecordService.findByMerchantId(merchantUuid));
    }

    @Test
    @WithAuthUser(email = "test@naver.com", role = "ROLE_USER")
    @DisplayName("스터디방 입장 로직(포인트 계산)-성공 케이스")
    public void testEnterStudyRoom_Valid() {
        Users hostUser = makeUser();

        when(authService.getUser()).thenReturn(hostUser);
        when(pointRecordService.getSumRecordByUser(hostUser)).thenReturn(1000L);

        Long deposit = 500L;
        pointRecordService.calEnterRoom(deposit);

        // Verify that the insertRecord method was called with the correct arguments
        verify(pointRecordService, times(1)).insertRecord(hostUser, deposit, PaymentType.DEPOSIT);
    }

    @Test
    @DisplayName("스터디방 입장 로직(포인트 계산)-실패 케이스")
    public void testEnterStudyRoom_InsufficientFunds() {
        Users hostUser = makeUser();

        when(authService.getUser()).thenReturn(hostUser);
        when(pointRecordService.getSumRecordByUser(hostUser)).thenReturn(1000L);

        Long deposit = 5000L;

        // Ensure that the PaymentException is thrown with the expected error code and status
        Assertions.assertThrows(
                PaymentException.class,
                () -> {
                    pointRecordService.calEnterRoom(deposit);
                }
        );
        verify(pointRecordService, never()).insertRecord(hostUser, deposit, PaymentType.DEPOSIT);
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
}