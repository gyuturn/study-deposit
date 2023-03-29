package com.study.deposit.domain.point.controller;

import com.study.deposit.domain.point.dto.PointOrderInfoDto;
import com.study.deposit.domain.point.dto.PointRecordPrepareDto;
import com.study.deposit.domain.point.service.IamPortService;
import com.study.deposit.domain.user.domain.Users;
import com.study.deposit.domain.user.service.AuthService;
import com.study.deposit.global.common.CommonResponse;
import com.study.deposit.global.common.code.CommonCode;
import com.study.deposit.global.common.code.iamPort.IamPortErrorCode;
import java.io.IOException;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/point/record/kakaopay/")
@Validated
public class KaKaoPayController implements  PointRecordController{
    private final IamPortService iamPortService;
    private final AuthService authService;

    @Override
    @PostMapping
    public ResponseEntity<CommonResponse> paymentComplete(PointOrderInfoDto pointOrderInfoDto)  {
        return null;
    }

    @Override
    @PostMapping("/payment/prepare")
    public ResponseEntity<CommonResponse> paymentPrepare(@RequestBody @Valid PointRecordPrepareDto dto) throws IOException {
        try {
            String token = iamPortService.getToken();
            iamPortService.paymentPrepare(dto, token);
        } catch (IOException e) {
            ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(CommonResponse.toResponse(IamPortErrorCode.UNAUTHORIZED_TOKEN));
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(CommonResponse.toResponse(CommonCode.OK));
    }




}
