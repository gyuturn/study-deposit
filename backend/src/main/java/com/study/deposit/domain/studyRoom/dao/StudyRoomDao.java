package com.study.deposit.domain.studyRoom.dao;

import com.study.deposit.domain.studyRoom.domain.StudyRoom;
import com.study.deposit.domain.user.domain.Users;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import javax.persistence.OrderBy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudyRoomDao extends JpaRepository<StudyRoom, Long> {
    List<StudyRoom> findAllByOrderByCreateDateDesc();
}
