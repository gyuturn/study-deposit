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

class UserResDtoTest {
    @Test
    @DisplayName("유저객체를 유저 DTO객체로 매핑")
    void makeDto() {
        //given
        Users user = Users.builder()
                .id(UUID.randomUUID())
                .nickName("test")
                .loginType(LoginType.KAKAO)
                .email("test@naver.com")
                .role(Role.USER)
                .build();
        //when
        UserResDto userResDto = UserResDto.makeDto(user);
        //then
        assertThat(userResDto.getNickName()).isEqualTo(user.getNickName());
        assertThat(userResDto.getEmail()).isEqualTo(user.getEmail());


    }
}