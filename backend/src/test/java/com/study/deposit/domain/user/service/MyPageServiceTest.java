package com.study.deposit.domain.user.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.study.deposit.domain.point.service.PointRecordService;
import com.study.deposit.domain.user.domain.LoginType;
import com.study.deposit.domain.user.domain.Role;
import com.study.deposit.domain.user.domain.Users;
import com.study.deposit.domain.user.dto.MyPageResDto;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class MyPageServiceTest {

    @Mock
    AuthService authService;
    @Mock
    PointRecordService pointRecordService;


    @InjectMocks
    MyPageService myPageService;

    @Test
    @DisplayName("마이페이지 홈 조회 시 dto 생성")
    void getMyPageHomeDto() {
        //given
        Users testUser = makeUser();
        Long testSumRecordOfUser = 100L;
        when(authService.getUser()).thenReturn(testUser);
        when(pointRecordService.getSumRecordByUser(testUser)).thenReturn(testSumRecordOfUser);

        //when
        MyPageResDto result = myPageService.getMyPageHomeDto();

        //then
        assertEquals(testUser.getNickName(), result.getNickName());
        assertEquals(testSumRecordOfUser, result.getSumOfChargeAmount());
        verify(authService).getUser();
        verify(pointRecordService).getSumRecordByUser(testUser);
    }

    private Users makeUser() {
        return Users.builder()
                .nickName("test")
                .loginType(LoginType.KAKAO)
                .email("test@naver.com")
                .role(Role.USER)
                .id(UUID.randomUUID()).build();
    }
}