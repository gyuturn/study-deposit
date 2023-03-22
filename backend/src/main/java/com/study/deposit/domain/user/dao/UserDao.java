package com.study.deposit.domain.user.dao;

import com.study.deposit.domain.user.domain.Users;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends JpaRepository<Users, UUID> {
    Optional<Users> findByEmail(String eMail);
}
