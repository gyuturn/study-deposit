package com.study.deposit.domain.studyRoom.service;

import com.study.deposit.domain.hashTag.service.HashTagService;
import com.study.deposit.domain.studyRoom.dao.StudyRoomDao;
import com.study.deposit.domain.studyRoom.dao.UserStudyRoomDao;
import com.study.deposit.domain.studyRoom.domain.StudyRoom;
import com.study.deposit.domain.studyRoom.domain.UserStudyRoom;
import com.study.deposit.domain.studyRoom.dto.StudyRoomMakingReqDto;
import com.study.deposit.domain.user.domain.Users;
import com.study.deposit.domain.user.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    /**
     * 1. 스티디방 생성
     * 2. 그와 관련된 해시태크도 저장
     * 3. 처음 방 만든 호스트도 UserStudRoom객체에 저장
     * 4. 방 생성 시 출석체크 시간대 고려?
     */
    @Transactional
    public void makeStudyRoom(StudyRoomMakingReqDto dto) {
        //스터디방 먼저 생성
        StudyRoom savedStudyRoom = studyRoomDao.save(StudyRoom.toEntity(dto));
        // 해시태그 스터디방에 맞게 매핑 및 저장
        hashTagService.hashTagsMapStudyRoom(dto.getHashTags(), savedStudyRoom);
        //방장을 스터디방에 저장
        Users host = authService.getUser();
        insertUserToStudyRoom(savedStudyRoom,host);
    }

    private void insertUserToStudyRoom(StudyRoom studyRoom,Users user) {
        userStudyRoomDao.save(UserStudyRoom.toEntityForHost(studyRoom, user));
    }


}
