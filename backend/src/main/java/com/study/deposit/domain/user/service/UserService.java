package com.study.deposit.domain.user.service;

import com.study.deposit.domain.user.dao.UserDao;
import com.study.deposit.domain.user.domain.Users;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserDao userDao;

    @Transactional
    public void updateNickName(Users user,String changeNickName) {
        Users updateNickName = user.updateNickName(changeNickName);
        userDao.save(updateNickName);
    }
}
