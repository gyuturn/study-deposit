package com.study.deposit.domain.point.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import lombok.Data;

@Data
public class PointRecordPrepareDto {
    @NotBlank
    @Size(min=1 , max=40)
    private String merchant_uid;
    @NotNull
    @Positive
    private Long amount;
}
