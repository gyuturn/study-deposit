package com.study.deposit.domain.point.service;

import com.study.deposit.domain.point.dao.PointRecordDao;
import com.study.deposit.domain.point.domain.PaymentType;
import com.study.deposit.domain.point.domain.PointRecord;
import com.study.deposit.domain.point.dto.PointRecordPrepareDto;
import com.study.deposit.domain.user.domain.Users;
import com.study.deposit.domain.user.service.AuthService;
import com.study.deposit.global.common.code.pointrecord.PointRecordErrorCode;
import com.study.deposit.global.common.code.studyroom.StudyRoomErrorCode;
import com.study.deposit.global.common.exception.DefaultException;
import com.study.deposit.global.common.exception.payment.PaymentException;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PointRecordService {

    private final PointRecordDao pointRecordDao;
    private final AuthService authService;

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

    /**
     * 금액 충전
     */
    @Transactional
    public void insertRecord(Users users, PointRecordPrepareDto pointRecordPrepareDto, PaymentType paymentType) {
        PointRecord newPointRecord = PointRecord.makePointRecord(users, pointRecordPrepareDto,paymentType);
        pointRecordDao.save(newPointRecord);
        log.info("{}의 충전 point:{}", users.getNickName(), pointRecordPrepareDto.getAmount());
    }

    /**
     * 포인트가 차감될때 해당 메서드 사용
     */
    @Transactional
    public void insertRecord(Users users, Long paymentAmount, PaymentType paymentType) {
        PointRecord newPointRecord = PointRecord.makePointRecord(users, paymentAmount,paymentType);
        pointRecordDao.save(newPointRecord);
        log.info("{}의 사용 포인트 point:{}", users.getNickName(), paymentAmount);
    }

    @Transactional
    public void deleteRecord(PointRecord pointRecord) {
        log.info("pointRecord 삭제: {}", pointRecord.getMerchant_uid());
        pointRecordDao.delete(pointRecord);
    }


    //금액 id로 찾기
    public PointRecord findByMerchantId(String merchant_uuid) {
        Optional<PointRecord> pointRecord = pointRecordDao.findById(merchant_uuid);
        if (pointRecord.isEmpty()) {
            log.error("{}: 에 해당하는 pointrecord가 존재하지 않음", merchant_uuid);
            throw new DefaultException(PointRecordErrorCode.NOT_EXIST_POINT_RECORD, HttpStatus.NOT_FOUND);
        }
        return pointRecord.get();
    }

    /**
     * 스터디방 입장(방 만들때도 공통 사용) 입장시 금액이 부족하면 402 에러
     */
    public void calEnterRoom(Long studyRoomsDeposit) {
        Users enterUser = authService.getUser();
        Long sumRecordByEnterUser = getSumRecordByUser(enterUser);

        //스터디 보증금, 사용자 보유 포인트 비교
        checkValidEnterRoom(studyRoomsDeposit, sumRecordByEnterUser);
        //포인트 사용(마이너스 주의!!)
        insertRecord(enterUser, -studyRoomsDeposit, PaymentType.DEPOSIT);
        log.info("스터디방 입장, 포인트 차감:{}", studyRoomsDeposit);
    }

    private void checkValidEnterRoom(Long studyRoomsDeposit, Long sumRecordByEnterUser) {
        if ((sumRecordByEnterUser < studyRoomsDeposit)) {
            //유저가 입장 보증금보다 작은 포인트를 가진 경우 예외 터트리기
            log.error("스터디방 입장, 포인트 부족, 보유포인트:{}, 보증금:{}", sumRecordByEnterUser, studyRoomsDeposit);
            throw new PaymentException(StudyRoomErrorCode.NOT_ENOUGH_POINT_FOR_DEPOSIT, HttpStatus.PAYMENT_REQUIRED);
        }
    }

}
