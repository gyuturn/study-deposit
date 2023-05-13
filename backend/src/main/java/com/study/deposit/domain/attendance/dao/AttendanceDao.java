package com.study.deposit.domain.attendance.dao;

import com.study.deposit.domain.attendance.domain.Attendance;
import com.study.deposit.domain.studyRoom.domain.StudyRoom;
import com.study.deposit.domain.user.domain.Users;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttendanceDao  extends JpaRepository<Attendance, Long> {
    public List<Attendance> findByUsersAndStudyRoom(Users users, StudyRoom studyRoom);
}

