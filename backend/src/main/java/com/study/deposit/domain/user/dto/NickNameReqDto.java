package com.study.deposit.domain.user.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.Data;

@Data
public class NickNameReqDto {
    @NotBlank
    @Size(min=1 , max=10 )
    private String newNickName;
}
