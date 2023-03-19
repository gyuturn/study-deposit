package com.study.deposit.domain.user.controller;


import com.study.deposit.domain.user.dto.NickNameReqDto;
import com.study.deposit.domain.user.service.AuthService;
import com.study.deposit.domain.user.service.UserService;
import com.study.deposit.global.common.CommonResponse;
import com.study.deposit.global.common.code.CommonCode;
import com.study.deposit.global.common.code.user.UserErrorCode;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
@Validated
public class UserController {
    private final AuthService authService;
    private final UserService userService;


    @Operation(summary = "닉네임 변경 api", description = "사용자 닉네임 변경")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "변경 성공"),
                    @ApiResponse(responseCode = "401", description = "사용자 확인 불가")
            }
    )
    @PatchMapping("/nickname")
    public ResponseEntity<CommonResponse> updateNickname(@RequestBody @Valid NickNameReqDto reqDto) {
        userService.updateNickName(authService.getUser(), reqDto.getNickName());

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(CommonResponse.toResponse(CommonCode.OK));
    }


    @Operation(summary = "닉네임 중복조회 api", description = "사용자 닉네임 중복조회")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "사용가능한 유저 닉네임"),
                    @ApiResponse(responseCode = "409", description = "사용 불가능한 유저 닉네임(중복된 유저)")
            }
    )
    @GetMapping("/nickname/valid")
    public ResponseEntity<CommonResponse> checkDuplicate(@RequestParam String reqNickName) {
        //유저가 중복된 이름을 가진경우
        if (!userService.validNickName(reqNickName)) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(CommonResponse.toResponse(UserErrorCode.DUP_NICKNAME));
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(CommonResponse.toResponse(CommonCode.OK));
    }

}
