package com.study.deposit.domain.user.service;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.study.deposit.domain.user.dao.UserDao;
import com.study.deposit.domain.user.domain.LoginType;
import com.study.deposit.domain.user.domain.Role;
import com.study.deposit.domain.user.domain.Users;
import java.util.Arrays;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserDao userDao;
    @InjectMocks
    private UserService userService;

    @Test
    @DisplayName("사용자 닉네임 변경 로직 ")
    public void testUpdateNickName() {
        //given
        String kakaoEmail = "test@kakao.com";
        Users before = Users.builder()
                .nickName("before")
                .loginType(LoginType.KAKAO)
                .email(kakaoEmail)
                .role(Role.USER)
                .id(UUID.randomUUID()).build();

        String changeNickName = "newNickName";
        Users after = Users.builder()
                .nickName(changeNickName)
                .loginType(LoginType.KAKAO)
                .email(kakaoEmail)
                .role(Role.USER)
                .id(UUID.randomUUID()).build();

        when(userDao.save(before)).thenReturn(after);

        //when
        userService.updateNickName(before, changeNickName);

        // assert
        verify(userDao, times(1)).save(before);
    }


    @Test
    @DisplayName("유저가 중복된 닉네임을 중복검사 하는경우")
    void testCheckDupNickNameReturnsFalseWhenNickNameIsDuplicated() {
        // given
        String reqNickName = "sameNickName";
        Users user1 = makeUser().updateNickName("sameNickName");
        Users user2 = makeUser().updateNickName("nickName");
        when(userDao.findAll()).thenReturn(Arrays.asList(user1, user2));

        // when
        boolean result = userService.validNickName(reqNickName);

        // then
        assertFalse(result);
        verify(userDao, times(1)).findAll();
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
