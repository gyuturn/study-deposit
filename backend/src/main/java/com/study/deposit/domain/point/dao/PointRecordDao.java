package com.study.deposit.domain.point.dao;

import com.study.deposit.domain.point.domain.PointRecord;
import com.study.deposit.domain.user.domain.Users;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PointRecordDao extends JpaRepository<PointRecord, String> {
    List<PointRecord> findByUsers(Users users);

}
