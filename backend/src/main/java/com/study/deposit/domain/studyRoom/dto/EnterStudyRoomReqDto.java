package com.study.deposit.domain.studyRoom.dto;

import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class EnterStudyRoomReqDto {
    @NotNull
    private Long id;
}
