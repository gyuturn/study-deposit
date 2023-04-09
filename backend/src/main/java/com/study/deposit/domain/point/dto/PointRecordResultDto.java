package com.study.deposit.domain.point.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PointRecordResultDto {
    private Long chargeAmount;
    private String status;
}
