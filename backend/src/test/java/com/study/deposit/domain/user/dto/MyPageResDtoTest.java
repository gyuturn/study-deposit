package com.study.deposit.domain.user.dto;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import com.study.deposit.domain.user.domain.LoginType;
import com.study.deposit.domain.user.domain.Role;
import com.study.deposit.domain.user.domain.Users;
import java.util.UUID;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MyPageResDtoTest {

    @Test
    @DisplayName("user객체와 포인트 총합을 통한 Dto생성")
    void getDto() {
        //given
        Users users = makeUser();
        Long sumOfCharge = 1000L;
        //when
        MyPageResDto dto = MyPageResDto.getDto(users, sumOfCharge);
        //then
        assertThat(dto.getEmail()).isEqualTo(users.getEmail());
        assertThat(dto.getNickName()).isEqualTo(users.getNickName());
        assertThat(dto.getSumOfChargeAmount()).isEqualTo(sumOfCharge);

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