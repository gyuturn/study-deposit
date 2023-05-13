package com.study.deposit.domain.attendance.service;

import com.study.deposit.domain.attendance.dao.AttendanceDao;
import com.study.deposit.domain.attendance.domain.Attendance;
import com.study.deposit.domain.attendance.domain.AttendanceState;
import com.study.deposit.domain.attendance.dto.PostAttendanceReqDto;
import com.study.deposit.domain.studyRoom.domain.StudyRoom;
import com.study.deposit.domain.user.domain.Users;
import java.time.LocalTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class AttendanceService {
    private final AttendanceDao attendanceDao;
    private static final int ALLOWED_ATTENDANCE_MINUTE=5;


    /**
     * 출석시간은 해당 방의 출석시간 기준으로 +-5분의 간격까지 허용
     * @param studyRoom
     * @param users
     * @param reqDto
     */
    @Transactional
    public Attendance postAttendance(StudyRoom studyRoom, Users users, PostAttendanceReqDto reqDto) {
        //스터디방 설정 시간
        LocalTime beforeReqTime = reqDto.getReqTime().toLocalTime().minusMinutes(ALLOWED_ATTENDANCE_MINUTE);
        LocalTime afterReqTime = reqDto.getReqTime().toLocalTime().plusMinutes(ALLOWED_ATTENDANCE_MINUTE);
        //출석시간기준 5분간격 이내면 출석인정
        Attendance attendance = Attendance.builder()
                .attendanceTime(reqDto.getReqTime())
                .studyRoom(studyRoom)
                .users(users)
                .build();
        calAttendance(studyRoom, beforeReqTime, afterReqTime, attendance);
        return attendanceDao.save(attendance);
    }

    private void calAttendance(StudyRoom studyRoom, LocalTime beforeReqTime, LocalTime afterReqTime,
                           Attendance attendance) {
        if (studyRoom.getAttendanceTime().isAfter(beforeReqTime) &&
                studyRoom.getAttendanceTime().isBefore(afterReqTime)) {
            attendance.updateAttendanceState(AttendanceState.Attendance); //정상 출석
        } else {
            attendance.updateAttendanceState(AttendanceState.Tardiness); //지각
        }
    }
}
