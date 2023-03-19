package com.study.deposit.domain.user.service;

import com.study.deposit.domain.user.dao.UserDao;
import com.study.deposit.domain.user.domain.Users;
import com.study.deposit.global.common.code.auth.AuthErrorCode;
import com.study.deposit.global.common.code.user.UserErrorCode;
import com.study.deposit.global.common.exception.auth.AuthException;
import com.study.deposit.global.common.exception.user.UserModifyException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserDao userDao;

    @Transactional
    public void updateNickName(Users user,String changeNickName) {
        log.info("변경요청 nickname:{}, 이전 닉네임:{}",changeNickName,user.getNickName());
        if (!validNickName(changeNickName)) {
            log.error("중복된 닉네임:{}",changeNickName);
            throw new UserModifyException(UserErrorCode.DUP_NICKNAME, HttpStatus.CONFLICT);
        }
        Users updateNickName = user.updateNickName(changeNickName);
        userDao.save(updateNickName);
    }

    //닉네임 중복조회
    public boolean validNickName(String reqNickName) {
        return userDao.findAll().stream()
                .noneMatch(user -> reqNickName.equals(user.getNickName()));
    }

    public boolean checkModifyNickName(Users user) {
        Optional<Users> userInDb = userDao.findById(user.getId());
        checkPresent(userInDb);
        log.info("생성시간: {}, 수정시간: {}",userInDb.get().getCreatedDate(),userInDb.get().getModifiedDate());
        return userInDb.get().getCreatedDate().equals(userInDb.get().getModifiedDate());
    }

    private void checkPresent(Optional<Users> userInDb) {
        if (userInDb.isEmpty()) {
            throw new AuthException(AuthErrorCode.GUEST_USER, HttpStatus.UNAUTHORIZED);
        }
    }
}
