package com.study.deposit.domain.point.controller;

import com.study.deposit.domain.point.dto.PointOrderInfoDto;
import com.study.deposit.domain.point.dto.PointRecordCompleteDto;
import com.study.deposit.domain.point.dto.PointRecordPrepareDto;
import com.study.deposit.global.common.CommonResponse;
import java.io.IOException;
import javax.validation.Valid;
import org.json.simple.parser.ParseException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

public interface PointRecordController {

    ResponseEntity<CommonResponse> paymentComplete(@RequestBody PointRecordCompleteDto pointOrderInfoDto)
            throws IOException, ParseException;

    ResponseEntity<CommonResponse> paymentPrepare(@RequestBody @Valid PointRecordPrepareDto dto) throws IOException;

    ResponseEntity<CommonResponse> paymentResult(@RequestParam String imp_uid);
}
