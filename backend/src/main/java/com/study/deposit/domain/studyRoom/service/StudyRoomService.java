package com.study.deposit.domain.studyRoom.service;

import com.study.deposit.domain.hashTag.service.HashTagService;
import com.study.deposit.domain.point.service.PointRecordService;
import com.study.deposit.domain.studyRoom.dao.StudyRoomDao;
import com.study.deposit.domain.studyRoom.dao.UserStudyRoomDao;
import com.study.deposit.domain.studyRoom.domain.StudyRoom;
import com.study.deposit.domain.studyRoom.domain.StudyRoomRole;
import com.study.deposit.domain.studyRoom.domain.UserStudyRoom;
import com.study.deposit.domain.studyRoom.dto.StudyRoomInfoResDto;
import com.study.deposit.domain.studyRoom.dto.StudyRoomMakingReqDto;
import com.study.deposit.domain.user.domain.Users;
import com.study.deposit.domain.user.service.AuthService;
import com.study.deposit.global.common.code.studyroom.StudyRoomErrorCode;
import com.study.deposit.global.common.exception.studyroom.StudyRoomException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
        insertUserToStudyRoom(savedStudyRoom, host, StudyRoomRole.HOST);
        pointRecordService.calEnterRoom(savedStudyRoom.getDeposit());
        log.info("스터디방 생성 후, 방장을 스터디방에 입장");
    }

    private void insertUserToStudyRoom(StudyRoom studyRoom, Users user, StudyRoomRole studyRoomRole) {
        userStudyRoomDao.save(UserStudyRoom.toEntityForHost(studyRoom, user, studyRoomRole));
    }

    /**
     * 스터디방 입장 입장시 금액이 부족하면 402 에러 일반 User 전용 이미 들어가 있는 방이면 에러 처리
     */
    public void enterStudyRoom(Long studyRoomId) {
        StudyRoom studyRoom = studyRoomDao.findById(studyRoomId).get();
        Users enterUser = authService.getUser();

        //이미 해당 유저가 방에 들어가있는지 확인
        checkAlreadyEnteredRoom(studyRoom, enterUser);

        pointRecordService.calEnterRoom(studyRoom.getDeposit()); //포인트 계산
        insertUserToStudyRoom(studyRoom, enterUser, StudyRoomRole.USER); //인원 계싼
        log.info("스터디방 입장");
        log.info("스터디방:{}, 입장유저:{}", studyRoom.getId(), enterUser.getId());
    }

    private void checkAlreadyEnteredRoom(StudyRoom studyRoom, Users enterUser) {
        List<UserStudyRoom> userStudyRooms = userStudyRoomDao.findByStudyRoom(studyRoom);
        for (UserStudyRoom userStudyRoom : userStudyRooms) {
            if (enterUser.isEqualUser(userStudyRoom.getUsers())) {
                throw new StudyRoomException(StudyRoomErrorCode.ALREADY_ENTER_ROOM, HttpStatus.CONFLICT);
            }
        }
    }

    private List<StudyRoom> removeEnteredRoom(List<StudyRoom> studyRooms, Users enterUser) {
        List<StudyRoom> removedStudyRooms = new ArrayList<>();
        for (StudyRoom studyRoom : studyRooms) {
            boolean enterFlag = false;
            List<UserStudyRoom> userStudyRooms = userStudyRoomDao.findByStudyRoom(studyRoom);
            for (UserStudyRoom userStudyRoom : userStudyRooms) {
                if (enterUser.isEqualUser(userStudyRoom.getUsers())) {
                    enterFlag = true;
                    break;
                }
            }
            if(enterFlag==false){
                removedStudyRooms.add(studyRoom);
            }
        }
        log.info("자신이 속한방은 조회에서 제외");
        return removedStudyRooms;
    }

    private List<StudyRoom> removeFullCapacity(List<StudyRoom> studyRooms) {
        List<StudyRoom> removedStudyRooms = new ArrayList<>();
        for (StudyRoom studyRoom : studyRooms) {
            int nowAttendance = userStudyRoomDao.findByStudyRoom(studyRoom).size();
            if (studyRoom.getPersonCapacity() > nowAttendance) {
                removedStudyRooms.add(studyRoom);
            }
        }
        log.info("꽉찬 방은 조회에서 제외");
        return removedStudyRooms;

    }

    private List<StudyRoom> findAvailableRooms() {
        List<StudyRoom> studyRooms = studyRoomDao.findAllByOrderByCreateDateDesc();

        //이미 들어가있는 방은 삭제
//        List<StudyRoom> notIncludeOwn = removeEnteredRoom(studyRooms, authService.getUser());

        //인원이 꽉찬 방은 삭제
        return removeFullCapacity(studyRooms);
    }


    /**
     * 스터디방 리스트 조회(메인 홈페이지에서 사용) 생성날짜 기준으로 최근날짜 순으로 정렬 추후 필요하면 Page기능 추가?
     */
    public List<StudyRoomInfoResDto> getStudyRoomList() {
        log.info("스터디방 전체 조회");
        List<StudyRoomInfoResDto> studyRoomDtoList = new ArrayList<>();
        List<StudyRoom> availableRooms = findAvailableRooms();
        for (StudyRoom studyRoom : availableRooms) {
            studyRoomDtoList.add(StudyRoomInfoResDto.getEntity(
                    studyRoom,
                    hashTagService.getHashTagsByStudyRoom(studyRoom),
                    getNowOccupancy(studyRoom)));
        }
        return studyRoomDtoList;

    }

    /**
     * 나의 스터디방 리스트 조회(메인 홈페이지에서 사용) 생성날짜 기준으로 최근날짜 순으로 정렬
     * 마이페이지에서 사용
     */
    public List<StudyRoomInfoResDto> getStudyRoomList(Users users) {
        log.info("나의 스터디방  조회");
        List<StudyRoomInfoResDto> studyRoomDtoList = new ArrayList<>();
        List<StudyRoom> studyRooms = getMyStudyRoom(users);
        for (StudyRoom studyRoom : studyRooms) {
            studyRoomDtoList.add(StudyRoomInfoResDto.getEntity(
                    studyRoom,
                    hashTagService.getHashTagsByStudyRoom(studyRoom),
                    getNowOccupancy(studyRoom)));
        }
        return studyRoomDtoList;

    }

    private List<StudyRoom> getMyStudyRoom(Users users) {
        List<StudyRoom> studyRooms = new ArrayList<>();
        List<UserStudyRoom> userStudyRooms = userStudyRoomDao.findByUsers(users);
        for (UserStudyRoom userStudyRoom : userStudyRooms) {
            if (!studyRooms.contains(userStudyRoom.getStudyRoom())) {
                studyRooms.add(userStudyRoom.getStudyRoom());
            }
        }
        return studyRooms;
    }

    //특정 스터디방에 현재 참가중인 인원 조회
    private Long getNowOccupancy(StudyRoom studyRoom) {
        return Long.valueOf(userStudyRoomDao.findByStudyRoom(studyRoom).size());
    }

    public StudyRoom findStudyRoom(Long studyRoomId) {
        Optional<StudyRoom> optionalStudyRoom = studyRoomDao.findById(studyRoomId);
        if (optionalStudyRoom.isEmpty()) {
            throw new StudyRoomException(StudyRoomErrorCode.NOT_EXIST, HttpStatus.NOT_FOUND);
        }
        return optionalStudyRoom.get();
    }

    public StudyRoomInfoResDto getStudyRoomInfo(StudyRoom studyRoom) {
        return StudyRoomInfoResDto.getEntity(
                studyRoom,
                hashTagService.getHashTagsByStudyRoom(studyRoom),
                getNowOccupancy(studyRoom));
    }



}
