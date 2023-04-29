package com.study.deposit.domain.hashTag.service;

import com.study.deposit.domain.hashTag.dao.HashTagDao;
import com.study.deposit.domain.hashTag.dao.StudyRoomHashTagDao;
import com.study.deposit.domain.hashTag.domain.HashTag;
import com.study.deposit.domain.hashTag.domain.StudyRoomHashTag;
import com.study.deposit.domain.hashTag.dto.MakeHashTagReqDto;
import com.study.deposit.domain.studyRoom.domain.StudyRoom;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class HashTagService {
    private final StudyRoomHashTagDao studyRoomHashTagDao;
    private final HashTagDao hashTagDao;



    //스터디방 생성시 해시태그 매핑
    @Transactional
    public void hashTagsMapStudyRoom(List<HashTag> hashTags, StudyRoom savedStudyRoom) {
        List<StudyRoomHashTag> studyRoomHashTags = new ArrayList<>();
        for (HashTag hashTag : hashTags) {
            studyRoomHashTags.add(StudyRoomHashTag.toEntity(savedStudyRoom, hashTag));
        }
        studyRoomHashTagDao.saveAll(studyRoomHashTags);
    }

    /**
     * 해시태그 생성
     */
    @Transactional
    public void makeHashTag(MakeHashTagReqDto makeHashTagReqDto) {
        hashTagDao.save(HashTag.toEntity(makeHashTagReqDto));
    }

    /**
     * 해시태그 조회 사용자 입력으로 시작하는 태그 모두 조회
     */

    public List<HashTag> getHashTags(String input) {
        log.info("해시태그 조회, 조회 입력값: {}",input);
        return hashTagDao.findByTagNameContaining(input);
    }

    private boolean isDupHashTag(String tagName) {
        return hashTagDao.findByTagName(tagName).isPresent();
    }
}
