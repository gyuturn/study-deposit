package com.study.deposit.domain.studyRoom.controller;

import com.study.deposit.domain.hashTag.dao.StudyRoomHashTagDao;
import com.study.deposit.domain.hashTag.domain.HashTag;
import com.study.deposit.domain.hashTag.domain.StudyRoomHashTag;
import com.study.deposit.domain.studyRoom.dao.StudyRoomDao;
import com.study.deposit.domain.studyRoom.dao.UserStudyRoomDao;
import com.study.deposit.domain.studyRoom.domain.StudyRoom;
import com.study.deposit.domain.studyRoom.domain.UserStudyRoom;
import com.study.deposit.domain.studyRoom.dto.EnterStudyRoomReqDto;
import com.study.deposit.domain.studyRoom.dto.StudyRoomMakingReqDto;
import com.study.deposit.domain.studyRoom.service.StudyRoomService;
import com.study.deposit.domain.user.domain.Users;
import com.study.deposit.domain.user.dto.NickNameReqDto;
import com.study.deposit.domain.user.service.AuthService;
import com.study.deposit.global.common.CommonResponse;
import com.study.deposit.global.common.code.CommonCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/studyroom")
@Validated
public class StudyRoomController {
    private final StudyRoomService studyRoomService;


    @Operation(summary = "스터디방 생성 api", description = "스터디방 생성시에 요청하는 API")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "정상 생성"),
                    @ApiResponse(responseCode = "401", description = "사용자 확인 불가"),
                    @ApiResponse(responseCode = "402", description = "방 생성을 위한 포인트 부족"),
            }
    )
    @PostMapping
    public ResponseEntity<CommonResponse> createStudyRoom(@RequestBody @Valid StudyRoomMakingReqDto reqDto) {
        studyRoomService.makeStudyRoom(reqDto);
//        studyRoomService.enterStudyRoom(reqDto.getDeposit()); //스터디방 입장(보증금 포인트 차감) -> makeStudyRoom에 포함
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(CommonResponse.toResponse(CommonCode.CREATED));
    }

    @Operation(summary = "스터디방 조회 api", description = "메인 페이지에서 스터디방 리스트를 조회하는 API"
            + "추후 Pageable가능성 고려")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "정상 처리"),
                    @ApiResponse(responseCode = "401", description = "사용자 확인 불가"),
            }
    )
    @GetMapping
    public ResponseEntity<CommonResponse> getStudyRoomList() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(CommonResponse.toResponse(CommonCode.OK,studyRoomService.getStudyRoomList()));
    }

    @Operation(summary = "스터디방 입장 api", description = "스터디방 입장시 요청하는 API")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "정상 처리"),
                    @ApiResponse(responseCode = "401", description = "사용자 확인 불가"),
                    @ApiResponse(responseCode = "402", description = "방 입장을 위한 포인트 부족"),
            }
    )
    @PostMapping("/enter")
    public ResponseEntity<CommonResponse> enterStudyRoom(@RequestBody @Valid EnterStudyRoomReqDto dto) {
        studyRoomService.enterStudyRoom(dto.getId());
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(CommonResponse.toResponse(CommonCode.OK));
    }


}
