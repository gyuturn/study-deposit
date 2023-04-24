package com.study.deposit.domain.hashTag.service;

import com.study.deposit.domain.hashTag.dao.HashTagDao;
import com.study.deposit.domain.hashTag.dao.StudyRoomHashTagDao;
import com.study.deposit.domain.hashTag.domain.HashTag;
import com.study.deposit.domain.hashTag.domain.StudyRoomHashTag;
import com.study.deposit.domain.hashTag.dto.MakeHashTagReqDto;
import com.study.deposit.domain.studyRoom.domain.StudyRoom;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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

    private boolean isDupHashTag(String tagName) {
        return hashTagDao.findByTagName(tagName).isPresent();
    }
}
