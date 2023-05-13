package com.study.deposit.domain.studyRoom.dao;

import com.study.deposit.domain.hashTag.domain.StudyRoomHashTag;
import com.study.deposit.domain.studyRoom.domain.StudyRoom;
import com.study.deposit.domain.studyRoom.domain.UserStudyRoom;
import com.study.deposit.domain.user.domain.Users;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserStudyRoomDao extends JpaRepository<UserStudyRoom, Long> {
    List<UserStudyRoom> findByStudyRoom(StudyRoom studyRoom);
    List<UserStudyRoom> findByUsers(Users users);

    Optional<UserStudyRoom> findByStudyRoomAndUsers(StudyRoom studyRoom, Users users);
}

