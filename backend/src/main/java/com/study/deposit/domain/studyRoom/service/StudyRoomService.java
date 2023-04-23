package com.study.deposit.domain.studyRoom.service;

import com.study.deposit.domain.hashTag.service.HashTagService;
import com.study.deposit.domain.point.domain.PaymentType;
import com.study.deposit.domain.point.dto.PointRecordPrepareDto;
import com.study.deposit.domain.point.service.PointRecordService;
import com.study.deposit.domain.studyRoom.dao.StudyRoomDao;
import com.study.deposit.domain.studyRoom.dao.UserStudyRoomDao;
import com.study.deposit.domain.studyRoom.domain.StudyRoom;
import com.study.deposit.domain.studyRoom.domain.UserStudyRoom;
import com.study.deposit.domain.studyRoom.dto.StudyRoomMakingReqDto;
import com.study.deposit.domain.user.domain.Users;
import com.study.deposit.domain.user.service.AuthService;
import com.study.deposit.global.common.code.studyroom.StudyRoomErrorCode;
import com.study.deposit.global.common.exception.payment.PaymentException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class StudyRoomService {

    private final StudyRoomDao studyRoomDao;
    private final UserStudyRoomDao userStudyRoomDao;
    private final AuthService authService;
    private final HashTagService hashTagService;
    private final PointRecordService pointRecordService;

    /**
     * 1. 스티디방 생성 2. 그와 관련된 해시태크도 저장 3. 처음 방 만든 호스트도 UserStudRoom객체에 저장 4. 방 생성 시 출석체크 시간대 고려?
     */
    @Transactional
    public void makeStudyRoom(StudyRoomMakingReqDto dto) {
        //스터디방 먼저 생성
        StudyRoom savedStudyRoom = studyRoomDao.save(StudyRoom.toEntity(dto));
        log.info("스터디방 생성");
        // 해시태그 스터디방에 맞게 매핑 및 저장
        hashTagService.hashTagsMapStudyRoom(dto.getHashTags(), savedStudyRoom);
        log.info("스터디방에 적용되는 해시태그 매핑 완료");
        //방장을 스터디방에 저장
        Users host = authService.getUser();
        insertUserToStudyRoom(savedStudyRoom, host);
        log.info("스터디방 생성 후, 방장을 스터디방에 입장");
    }

    private void insertUserToStudyRoom(StudyRoom studyRoom, Users user) {
        userStudyRoomDao.save(UserStudyRoom.toEntityForHost(studyRoom, user));
    }

    /**
     * 스터디방 입장(방 만들때도 공통 사용) 입장시 금액이 부족하면 402 에러
     */
    public void enterStudyRoom(Long studyRoomsDeposit) {
        Users enterUser = authService.getUser();
        Long sumRecordByEnterUser = pointRecordService.getSumRecordByUser(enterUser);

        //스터디 보증금, 사용자 보유 포인트 비교
        checkValidEnterRoom(studyRoomsDeposit, sumRecordByEnterUser);
        //포인트 사용(마이너스 주의!!)
        pointRecordService.insertRecord(enterUser, -studyRoomsDeposit, PaymentType.PURCHASE);
        log.info("스터디방 입장, 포인트 차감:{}",studyRoomsDeposit);
    }

    private void checkValidEnterRoom(Long studyRoomsDeposit, Long sumRecordByEnterUser) {
        if ((sumRecordByEnterUser < studyRoomsDeposit)) {
            //유저가 입장 보증금보다 작은 포인트를 가진 경우 예외 터트리기
            log.error("스터디방 입장, 포인트 부족, 보유포인트:{}, 보증금:{}", sumRecordByEnterUser, studyRoomsDeposit);
            throw new PaymentException(StudyRoomErrorCode.NOT_ENOUGH_POINT_FOR_DEPOSIT, HttpStatus.PAYMENT_REQUIRED);
        }
    }


}
