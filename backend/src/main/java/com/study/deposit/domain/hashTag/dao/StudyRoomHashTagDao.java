package com.study.deposit.domain.hashTag.dao;

import com.study.deposit.domain.hashTag.domain.StudyRoomHashTag;
import com.study.deposit.domain.studyRoom.domain.StudyRoom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudyRoomHashTagDao extends JpaRepository<StudyRoomHashTag, Long> {
}
