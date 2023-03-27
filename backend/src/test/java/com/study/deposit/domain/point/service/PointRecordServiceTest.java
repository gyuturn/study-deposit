package com.study.deposit.domain.point.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import com.study.deposit.domain.point.dao.PointRecordDao;
import com.study.deposit.domain.point.domain.PointRecord;
import com.study.deposit.domain.user.domain.LoginType;
import com.study.deposit.domain.user.domain.Role;
import com.study.deposit.domain.user.domain.Users;
import com.study.deposit.domain.user.service.AuthService;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;

@ExtendWith(MockitoExtension.class)
class PointRecordServiceTest {
    @Mock
    PointRecordDao pointRecordDao;

    @InjectMocks
    private PointRecordService pointRecordService;



    @Test
    @DisplayName("포인트 총합계산 로직")
    public void testSumRecordByUser() {
        //given
        Users users = makeUser();

        List<PointRecord> pointRecords = makePointRecords(users);
        when(pointRecordDao.findByUsers(users)).thenReturn(pointRecords);
        //when
        Long sum = pointRecordService.sumRecordByUser(users);

        //then
        assertEquals(600L, sum);
    }

    private List<PointRecord> makePointRecords(Users users) {
        List<PointRecord> testRecords = new ArrayList<>();
        testRecords.add(new PointRecord(UUID.randomUUID(), users, LocalDateTime.now(), 100L));
        testRecords.add(new PointRecord(UUID.randomUUID(), users, LocalDateTime.now(), 200L));
        testRecords.add(new PointRecord(UUID.randomUUID(), users, LocalDateTime.now(), 300L));
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
}