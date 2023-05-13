package com.study.deposit.domain.attendance.service;

import com.study.deposit.domain.attendance.dao.AttendanceDao;
import com.study.deposit.domain.attendance.domain.Attendance;
import com.study.deposit.domain.attendance.domain.AttendanceState;
import com.study.deposit.domain.attendance.dto.AttendanceInfo;
import com.study.deposit.domain.attendance.dto.AttendanceListResDto;
import com.study.deposit.domain.attendance.dto.PostAttendanceReqDto;
import com.study.deposit.domain.studyRoom.dao.UserStudyRoomDao;
import com.study.deposit.domain.studyRoom.domain.StudyRoom;
import com.study.deposit.domain.studyRoom.domain.UserStudyRoom;
import com.study.deposit.domain.user.domain.Users;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class AttendanceService {
    private final AttendanceDao attendanceDao;
    private final UserStudyRoomDao userStudyRoomDao;

    private static final int ALLOWED_ATTENDANCE_MINUTE=5;


    /**
     * 출석시간은 해당 방의 출석시간 기준으로 +-5분의 간격까지 허용
     *
     * @param studyRoom
     * @param users
     * @param reqDto
     */
    @Transactional
    public Attendance postAttendance(StudyRoom studyRoom, Users users, PostAttendanceReqDto reqDto) {
        log.info("출석요청 요청 user:{}, studyRoom:{}", users.getId(), studyRoom.getId());
        //스터디방 설정 시간
          //출석시간기준 5분간격 이내면 출석인정
        Attendance attendance = Attendance.builder()
                .attendanceTime(reqDto.getReqTime())
                .studyRoom(studyRoom)
                .users(users)
                .build();
        calAttendance(studyRoom, reqDto.getReqTime().toLocalTime(), attendance);
        return attendanceDao.save(attendance);
    }

    //아직 출석시간 아닌지 체크
    public boolean checkNotAttendanceTime(LocalTime reqTime,StudyRoom studyRoom) {
        return reqTime.minusMinutes(ALLOWED_ATTENDANCE_MINUTE).isBefore(studyRoom.getAttendanceTime());
    }

    //오늘 출석체크를 했는지 check
    public boolean checkTodayAttendance(Users users, StudyRoom studyRoom) {
        List<Attendance> usersAttendance = attendanceDao.findByUsersAndStudyRoom(users, studyRoom);
        LocalDate currentDate = LocalDate.now();
        for (Attendance attendance : usersAttendance) {
            LocalDate attendanceDate = attendance.getAttendanceTime().toLocalDate();
            if (currentDate.isEqual(attendanceDate)) {
                log.info("이미 오늘 출석을 완료함 user:{}", users.getId());
                return true;
            }
        }
        return false;
    }

    public AttendanceListResDto getAttendanceList(StudyRoom studyRoom) {
        log.info("현재 출석현황 조회");
        AttendanceListResDto resDto = new AttendanceListResDto();
        updateAttendanceTime(studyRoom, resDto); //  resDto 출석시간 업데이트

        Map<Users, Long> attendanceInfoEachUser = new HashMap<>();
        List<Attendance> attendances = attendanceDao.findByStudyRoom(studyRoom);

        //출석일수 구하기
        calAttendanceCountEachUser(attendanceInfoEachUser, attendances);

        //각 유저마다의 결석일수/총 필요 출석일수(스터디방 입장 기준)
        for (Entry<Users, Long> usersAttendanceCountSet : attendanceInfoEachUser.entrySet()) {
            AttendanceInfo attendanceInfo = new AttendanceInfo();
            Users users = usersAttendanceCountSet.getKey();
            Long attendanceCount = usersAttendanceCountSet.getValue();

            long totalAttendanceCountByUser = getTotalAttendanceCountByUser(studyRoom, users);
            attendanceInfo.setTotalAttendanceDay(totalAttendanceCountByUser); //총 출석일(스터디방 입장날짜 기준)
            attendanceInfo.setUsersNickName(users.getNickName());
            attendanceInfo.setAbsenceDay(totalAttendanceCountByUser - attendanceCount); // 결석일 계산

            updateTodaysAttendance(studyRoom, attendanceInfo, users); //오늘 출석 업데이트

            resDto.getAttendanceInfo().add(attendanceInfo);
        }
        return resDto;
    }

    private void updateTodaysAttendance (StudyRoom studyRoom, AttendanceInfo attendanceInfo, Users users) {
        List<Attendance> attendanceList = attendanceDao.findByUsersAndStudyRoom(users, studyRoom);
        for (Attendance attendance : attendanceList) {
            if (attendance.getAttendanceTime().toLocalDate().equals(LocalDate.now())&&attendance.getAttendanceState().equals(AttendanceState.Attendance)) {
                attendanceInfo.setTodaysAttendance(true);
                break;
            }else{
                attendanceInfo.setTodaysAttendance(false);
            }
        }
    }

    private long getTotalAttendanceCountByUser(StudyRoom studyRoom, Users users) {
        UserStudyRoom userStudyRoom = userStudyRoomDao.findByStudyRoomAndUsers(studyRoom, users).get();
        LocalDate startDate = userStudyRoom.getEnterDate().toLocalDate();
        LocalDate endDate = studyRoom.getEndDate();

        long totalDateByUser = ChronoUnit.DAYS.between(startDate, endDate) + 1;
        return totalDateByUser;
    }

    private void calAttendanceCountEachUser(Map<Users, Long> attendanceInfoEachUser, List<Attendance> attendances) {
        for (Attendance attendance : attendances) {
            if (attendance.getAttendanceState().equals(AttendanceState.Attendance)) {
                Long attendanceCount = attendanceInfoEachUser.getOrDefault(attendance.getUsers(), Long.valueOf(0));
                attendanceInfoEachUser.put(attendance.getUsers(),attendanceCount+1);
            }else{
                Long attendanceCount = attendanceInfoEachUser.getOrDefault(attendance.getUsers(), Long.valueOf(0));
                attendanceInfoEachUser.put(attendance.getUsers(),attendanceCount);
            }
        }
    }

    private void updateAttendanceTime(StudyRoom studyRoom, AttendanceListResDto attendanceListResDto) {
        attendanceListResDto.setStartAttendanceTime(studyRoom.getAttendanceTime().minusMinutes(ALLOWED_ATTENDANCE_MINUTE));
        attendanceListResDto.setEndAttendanceTime(studyRoom.getAttendanceTime().plusMinutes(ALLOWED_ATTENDANCE_MINUTE));
    }


    private void calAttendance(StudyRoom studyRoom, LocalTime reqTime, Attendance attendance) {
        LocalTime beforeReqTime = reqTime.minusMinutes(ALLOWED_ATTENDANCE_MINUTE);
        LocalTime afterReqTime = reqTime.plusMinutes(ALLOWED_ATTENDANCE_MINUTE);

        if (studyRoom.getAttendanceTime().isAfter(beforeReqTime) &&
                studyRoom.getAttendanceTime().isBefore(afterReqTime)) {
            attendance.updateAttendanceState(AttendanceState.Attendance); //정상 출석
            log.info("정상 출석");
        } else {
            attendance.updateAttendanceState(AttendanceState.Tardiness); //지각
            log.info("지각 출석");
        }
    }
}
