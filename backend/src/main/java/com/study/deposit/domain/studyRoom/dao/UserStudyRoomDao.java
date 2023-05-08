package com.study.deposit.domain.studyRoom.dao;

import com.study.deposit.domain.hashTag.domain.StudyRoomHashTag;
import com.study.deposit.domain.studyRoom.domain.StudyRoom;
import com.study.deposit.domain.studyRoom.domain.UserStudyRoom;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserStudyRoomDao extends JpaRepository<UserStudyRoom, Long> {
    List<UserStudyRoom> findByStudyRoom(StudyRoom studyRoom);
}

