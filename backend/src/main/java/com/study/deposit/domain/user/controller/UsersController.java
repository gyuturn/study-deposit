package com.study.deposit.domain.user.controller;


import com.study.deposit.domain.point.service.PointRecordService;
import com.study.deposit.domain.studyRoom.service.StudyRoomService;
import com.study.deposit.domain.user.dto.NickNameReqDto;
import com.study.deposit.domain.user.service.AuthService;
import com.study.deposit.domain.user.service.MyPageService;
import com.study.deposit.domain.user.service.UserService;
import com.study.deposit.global.common.CommonResponse;
import com.study.deposit.global.common.code.CommonCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
@Validated
public class UsersController {
    private final AuthService authService;
    private final UserService userService;
    private final MyPageService myPageService;
    private final StudyRoomService studyRoomService;


    @Operation(summary = "닉네임 변경 api", description = "사용자 닉네임 변경")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "변경 성공"),
                    @ApiResponse(responseCode = "401", description = "사용자 확인 불가"),
                    @ApiResponse(responseCode = "409", description = "사용 불가능한 유저 닉네임(중복된 유저)")
            }
    )
    @PatchMapping("/nickname")

    public ResponseEntity<CommonResponse> updateNickname(@RequestBody @Valid NickNameReqDto reqDto) {
        userService.updateNickName(authService.getUser(), reqDto.getNewNickName());

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(CommonResponse.toResponse(CommonCode.OK));
    }

    @Operation(summary = "유저 로그인 or 회원가입인지 체크 api", description = "유저가 로그인하였는지 회원가입인지 체크하는 로직"
            + "추후 닉네임이 실명인경우 닉네임 변경하도록 권장하기 위함"
            + "db의 생성시간과 수정시간을 비교하여 일치하면 닉네임을 바꾸도록 권장")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "생성시간, 수정시간 불일치 -> 변경 필요없음"),
                    @ApiResponse(responseCode = "202", description = "생성시간, 수정시간 일치 -> 닉네임 변경 홈페이지로 redirect 필요")
            }
    )
    @GetMapping("/nickname/check")
    public ResponseEntity<CommonResponse> checkNickName() {
        if (userService.checkModifyNickName(authService.getUser())) {
            //생성, 수정시간이 같기에 닉네임 변경이 필요함
            return ResponseEntity
                    .status(HttpStatus.ACCEPTED)
                    .body(CommonResponse.toResponse(CommonCode.REDIRECT));
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(CommonResponse.toResponse(CommonCode.OK));
    }


    @Operation(summary = "포인트가 포함된 유저 정보 api", description = "마이페이지 접속시 필요한 정보를 얻어오기 위한 api")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "정상 처리"),
                    @ApiResponse(responseCode = "401", description = "로그인 되어 있지 않음")
            }
    )
    @GetMapping
    public ResponseEntity<CommonResponse> getUserInfoToMyPage() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(CommonResponse.toResponse(CommonCode.OK,myPageService.getMyPageHomeDto()));
    }

    @Operation(summary = "나의 스터디방 리스트 조회 api", description = "마이페이지의 스터디방 조회")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "정상 처리"),
                    @ApiResponse(responseCode = "401", description = "로그인 되어 있지 않음")
            }
    )
    @GetMapping("/studyrooms")
    public ResponseEntity<CommonResponse> getMyStudyRooms() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(CommonResponse.toResponse(CommonCode.OK,studyRoomService.getStudyRoomList(authService.getUser())));
    }

}
