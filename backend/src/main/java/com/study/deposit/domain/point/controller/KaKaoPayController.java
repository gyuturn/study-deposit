package com.study.deposit.domain.point.controller;

import com.study.deposit.domain.point.domain.PointRecord;
import com.study.deposit.domain.point.dto.PointRecordCompleteDto;
import com.study.deposit.domain.point.dto.PointRecordPrepareDto;
import com.study.deposit.domain.point.service.IamPortService;
import com.study.deposit.domain.point.service.PointRecordService;
import com.study.deposit.domain.user.service.AuthService;
import com.study.deposit.global.common.CommonResponse;
import com.study.deposit.global.common.code.CommonCode;
import com.study.deposit.global.common.code.iamPort.IamPortErrorCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.io.IOException;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.parser.ParseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
@RequiredArgsConstructor
@Validated
public class KaKaoPayController implements PointRecordController {
    private final IamPortService iamPortService;
    private final PointRecordService pointRecordService;
    private final AuthService authService;

    @Override
    @Operation(summary = "결제 완료 api", description = "아이엠포트에서 결제 webhook으로도 사용되며, 클라이언트에서 최종적으로"
            + "결제 완료시에 해당 api를 요청하고 결제를 완료한다.")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "정상 처리"),
                    @ApiResponse(responseCode = "404", description = "DB내에 결제 금액에 존재하지 않음"),
                    @ApiResponse(responseCode = "409", description = "결제 유효성검사(결제금액 동일)에서 실패")
            }
    )
    @PostMapping("/api/v2/point/record/kakaopay/payment/complete")
    public ResponseEntity<CommonResponse> paymentComplete(@RequestBody PointRecordCompleteDto dto)
            throws IOException, ParseException {

        String token = iamPortService.getToken();
        Long chargeForIamPort = iamPortService.paymentInfo(dto.getImp_uid(), token); //iamport에서 가져오는 결제 금액
        PointRecord chargeForDb = pointRecordService.findByMerchantId(dto.getMerchant_uid());//DB에서 가져오는 금액
        if (!iamPortService.validPayment(chargeForIamPort, chargeForDb)) {
            //결제가 유효하지 않은 경우
            iamPortService.rollbackPayment(chargeForDb,token, dto.getImp_uid());
        }

        return ResponseEntity.status(HttpStatus.OK)
                .body(CommonResponse.toResponse(CommonCode.OK));
    }

    @Override
    @Operation(summary = "사전결제 api", description = "프론트에서 결제창을 보여주기전 iamport 사전결제 api를 호출하여 유효성 검사를 사전에 먼저"
            + "진행한다.")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "정상 처리"),
                    @ApiResponse(responseCode = "401", description = "iam port 사전검증처리에서 token인증 실패"),
            }
    )
    @PostMapping("/api/v1/point/record/kakaopay/payment/prepare")
    public ResponseEntity<CommonResponse> paymentPrepare(@RequestBody @Valid PointRecordPrepareDto dto) {
        try {
            String token = iamPortService.getToken();
            iamPortService.paymentPrepare(dto, token);
            pointRecordService.insertRecord(authService.getUser(), dto);
        } catch (Exception e) {
            ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(CommonResponse.toResponse(IamPortErrorCode.UNAUTHORIZED_TOKEN));
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(CommonResponse.toResponse(CommonCode.OK));
    }


}
