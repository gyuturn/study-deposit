package com.study.deposit.domain.user.controller;

import com.study.deposit.domain.user.dao.UserDao;
import com.study.deposit.domain.user.dto.NickNameReqDto;
import com.study.deposit.domain.user.dto.UserResDto;
import com.study.deposit.domain.user.service.AuthService;
import com.study.deposit.global.common.CommonResponse;
import com.study.deposit.global.common.code.CommonCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
@Validated
public class AuthController {

    private final AuthService authService;

    @Operation(summary = "로그아웃 api", description = "세션 삭제, 쿠키 삭제")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "정상 처리"),
                    @ApiResponse(responseCode = "401", description = "사용자 확인 불가"),
            }
    )
    @DeleteMapping
    public ResponseEntity<CommonResponse> logout(HttpServletRequest request, HttpServletResponse response) {
        authService.logout(request, response);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(CommonResponse.toResponse(CommonCode.OK));
    }

    @Operation(summary = "유저 정보 가져오기 api", description = "유저의 정보를 제공합니다.")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "정상 처리"),
                    @ApiResponse(responseCode = "401", description = "사용자 확인 불가"),
            }
    )
    @GetMapping
    public ResponseEntity<CommonResponse> getUserInfo() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(CommonResponse.toResponse(CommonCode.OK, UserResDto.makeDto(authService.getUser())));
    }
}
