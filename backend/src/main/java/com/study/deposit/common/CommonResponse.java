package com.study.deposit.common;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.study.deposit.common.code.Code;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Builder
@RequiredArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommonResponse {
    private final String timeStamp = ZonedDateTime.now(ZoneId.of("Asia/Seoul"))
            .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    private final String code;
    private final String message;
    private final Object data;


    public static CommonResponse toResponse(Code code, Object data) {
        return CommonResponse.builder()
                .code(code.getCode())
                .message(code.getMessage())
                .data(data)
                .build();
    }

    public static CommonResponse toResponse(Code commonCode) {
        return CommonResponse.builder()
                .code(commonCode.getCode())
                .message(commonCode.getMessage())
                .build();
    }

    public static CommonResponse toErrorResponse(Code code) {
        return CommonResponse.builder()
                .code(code.getCode())
                .message(code.getMessage())
                .build();
    }

    public static CommonResponse toErrorResponse(Code code,String message) {
        return CommonResponse.builder()
                .code(code.getCode())
                .message(message)
                .build();
    }


}