package com.study.deposit.domain.attendance.controller;

import com.study.deposit.domain.attendance.dao.AttendanceDao;
import com.study.deposit.domain.attendance.domain.Attendance;
import com.study.deposit.domain.attendance.domain.AttendanceState;
import com.study.deposit.domain.attendance.dto.AttendanceListResDto;
import com.study.deposit.domain.attendance.dto.PostAttendanceReqDto;
import com.study.deposit.domain.attendance.service.AttendanceService;
import com.study.deposit.domain.studyRoom.dao.StudyRoomDao;
import com.study.deposit.domain.studyRoom.domain.StudyRoom;
import com.study.deposit.domain.studyRoom.dto.StudyRoomMakingReqDto;
import com.study.deposit.domain.studyRoom.service.StudyRoomService;
import com.study.deposit.domain.user.service.AuthService;
import com.study.deposit.global.common.CommonResponse;
import com.study.deposit.global.common.code.CommonCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.time.LocalDateTime;
import java.time.LocalTime;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/studyroom/attendance")
@Validated
public class AttendanceController {
    private final StudyRoomDao studyRoomDao;
    private final AuthService authService;
    private final AttendanceService attendanceService;

    @Operation(summary = "출석체크 api", description = "현재의 시간과 스터디방에서 지정한 시간을 기준으로 출석체크 상태를 저장 및 보관")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "정상 처리"),
                    @ApiResponse(responseCode = "202", description = "지각처리"),
                    @ApiResponse(responseCode = "204", description = "아직 출석기간이 아님"),
                    @ApiResponse(responseCode = "401", description = "사용자 확인 불가"),
                    @ApiResponse(responseCode = "409", description = "이미 오늘출석을 완료함"),
            }
    )
    @PostMapping
    public ResponseEntity<CommonResponse> postAttendance(@RequestBody @Valid PostAttendanceReqDto reqDto) {
        StudyRoom studyRoom = studyRoomDao.findById(reqDto.getStudyRoomId()).get();

        //만약 이미 출석을 했을경우 CONFLICT
        if (attendanceService.checkTodayAttendance(authService.getUser(), studyRoom)) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(CommonResponse.toResponse(CommonCode.CONFLICT));
        }

        //아직 출석시간이 아닌경우
        if (attendanceService.checkNotAttendanceTime(reqDto.getReqTime().toLocalTime(),studyRoom)) {
            return ResponseEntity
                    .status(HttpStatus.NO_CONTENT)
                    .body(CommonResponse.toResponse(CommonCode.NO_CONTENT));
        }

        Attendance attendance = attendanceService.postAttendance(studyRoom, authService.getUser(), reqDto);
        return diffResponseForAttendance(attendance);
    }

    @Operation(summary = "현재 출석 현황 api", description = "/studyroom/attendance 에 필요한 데이터 제공")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "정상 처리"),
                    @ApiResponse(responseCode = "401", description = "사용자 확인 불가"),
            }
    )
    @GetMapping("/list/{id}")
    public ResponseEntity<CommonResponse> getAttendances(@PathVariable("id") Long studyRoomId) {
        StudyRoom studyRoom = studyRoomDao.findById(studyRoomId).get();
        AttendanceListResDto attendanceListResDto = attendanceService.getAttendanceList(studyRoom);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(CommonResponse.toResponse(CommonCode.OK,attendanceListResDto));
    }



    private ResponseEntity<CommonResponse> diffResponseForAttendance(Attendance attendance) {
        if (!attendance.getAttendanceState().equals(AttendanceState.Attendance)) {
            return ResponseEntity
                    .status(HttpStatus.ACCEPTED)
                    .body(CommonResponse.toResponse(CommonCode.ACCEPTED));
        } else {
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(CommonResponse.toResponse(CommonCode.CREATED));
        }
    }
}
