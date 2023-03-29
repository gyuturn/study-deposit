package com.study.deposit.domain.point.controller;

import com.study.deposit.domain.point.dto.PointOrderInfoDto;
import com.study.deposit.domain.point.dto.PointRecordPrepareDto;
import com.study.deposit.global.common.CommonResponse;
import java.io.IOException;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

public interface PointRecordController {

    ResponseEntity<CommonResponse> paymentComplete(PointOrderInfoDto pointOrderInfoDto) throws IOException;

    ResponseEntity<CommonResponse> paymentPrepare(@RequestBody @Valid PointRecordPrepareDto dto) throws IOException;
}
