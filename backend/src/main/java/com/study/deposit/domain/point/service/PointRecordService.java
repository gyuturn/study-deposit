package com.study.deposit.domain.point.service;

import com.study.deposit.domain.point.dao.PointRecordDao;
import com.study.deposit.domain.point.domain.PointRecord;
import com.study.deposit.domain.user.domain.Users;
import com.study.deposit.global.common.code.pointrecord.PointRecordErrorCode;
import com.study.deposit.global.common.exception.DefaultException;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PointRecordService {

    private final PointRecordDao pointRecordDao;

    //금액 합계
    public Long getSumRecordByUser(Users users) {
        long sum = 0;
        List<PointRecord> usersPoint = pointRecordDao.findByUsers(users);
        for (PointRecord pointRecord : usersPoint) {
            sum += pointRecord.getChargeAmount();
        }
        log.info("{}의 point 총합:{}", users.getNickName(), sum);
        return sum;
    }

    //금액 충전
    public void insertRecord(Users users,Long chargeAmount) {
        PointRecord newPointRecord = PointRecord.makePointRecord(users, chargeAmount);
        pointRecordDao.save(newPointRecord);
        log.info("{}의 충전 point:{}", users.getNickName(), chargeAmount);
    }

    //금액 id로 찾기
    public PointRecord findByMerchantId(String merchant_uuid) {
        Optional<PointRecord> pointRecord = pointRecordDao.findById(merchant_uuid);
        if (pointRecord.isEmpty()) {
            throw new DefaultException(PointRecordErrorCode.NOT_EXIST_POINT_RECORD, HttpStatus.NOT_FOUND)
        }
        return pointRecord.get();
    }
}
