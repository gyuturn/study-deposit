package com.study.deposit.domain.point.dao;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import com.study.deposit.domain.point.domain.PointRecord;
import com.study.deposit.domain.user.dao.UserDao;
import com.study.deposit.domain.user.domain.LoginType;
import com.study.deposit.domain.user.domain.Role;
import com.study.deposit.domain.user.domain.Users;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
class PointRecordDaoTest {
    @Autowired
    PointRecordDao pointRecordDao;
    @Autowired
    UserDao userDao;

    @Test
    @DisplayName("pointRecord 저장 및 조회")
    void savePointRecordByUsers() {
        //given
        Users savedUsers = saveUsers();
        long chargeAmount = 10000;
        //when
        PointRecord newPointRecord = PointRecord.builder()
                .merchant_uid(UUID.randomUUID().toString())
                .chargeAmount(chargeAmount)
                .users(savedUsers)
                .chargeDate(LocalDateTime.now())
                .build();

        pointRecordDao.save(newPointRecord);
        //then
        List<PointRecord> pointList = pointRecordDao.findByUsers(savedUsers);
        assertThat(pointList.size()).isEqualTo(1);
        assertThat(pointList.get(0).getUsers()).isSameAs(savedUsers);
    }

    private Users saveUsers() {
        Users users = Users.builder()
                .nickName("test")
                .loginType(LoginType.KAKAO)
                .email("test@naver.com")
                .role(Role.USER)
                .id(UUID.randomUUID()).build();
        return userDao.save(users);
    }
}