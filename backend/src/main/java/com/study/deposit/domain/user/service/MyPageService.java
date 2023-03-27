package com.study.deposit.domain.user.service;

import com.study.deposit.domain.point.service.PointRecordService;
import com.study.deposit.domain.user.domain.Users;
import com.study.deposit.domain.user.dto.MyPageResDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class MyPageService {
    private final AuthService authService;
    private final PointRecordService pointRecordService;

    public MyPageResDto getMyPageHomeDto() {
        Users user = authService.getUser();
        Long sumRecordOfUser = pointRecordService.getSumRecordByUser(user);
        return MyPageResDto.getDto(user, sumRecordOfUser);
    }
}
