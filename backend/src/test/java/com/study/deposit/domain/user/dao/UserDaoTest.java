package com.study.deposit.domain.user.dao;

import static org.junit.jupiter.api.Assertions.*;

import com.study.deposit.domain.user.domain.LoginType;
import com.study.deposit.domain.user.domain.Role;
import com.study.deposit.domain.user.domain.Users;
import java.util.UUID;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserDaoTest {
    @Autowired
    UserDao userDao;

    @Test
    @DisplayName("유저 변경 로직 dao")
    void updateUserNickName() {
        //give
        String before = "before";
        String after = "after";
        Users beforeUser = Users.builder()
                .nickName(before)
                .loginType(LoginType.KAKAO)
                .email("test@naver.com")
                .role(Role.USER)
                .id(UUID.randomUUID()).build();

        //when
        beforeUser.updateNickName(after);
        Users result = userDao.save(beforeUser);
        //then
        Assertions.assertThat(result.getNickName()).isEqualTo(after);
    }


    @Test
    @DisplayName("유저 생성시간 수정시간 같은지 테스트 dao")
    void checkModifyNickName() {
        //give
        Users beforeUser = Users.builder()
                .nickName("test")
                .loginType(LoginType.KAKAO)
                .email("test@naver.com")
                .role(Role.USER)
                .id(UUID.randomUUID()).build();
        Users saveUser = userDao.save(beforeUser);
        //when
        boolean result = saveUser.getCreatedDate().equals(saveUser.getModifiedDate());
        //then
        Assertions.assertThat(result).isTrue();
    }


}