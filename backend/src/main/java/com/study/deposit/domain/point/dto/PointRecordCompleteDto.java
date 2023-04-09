package com.study.deposit.domain.point.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PointRecordCompleteDto {
    private String imp_uid;
    private String merchant_uid;
    private String status;
}
