package com.study.deposit.domain.user.dto;

import com.study.deposit.domain.user.domain.Users;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MyPageResDto {
    @NotNull
    private String nickName;
    @NotNull
    private Long sumOfChargeAmount;
    @Email
    private String email;

    public static MyPageResDto getDto(Users users, Long sumOfChargeAmount) {
        return MyPageResDto.builder()
                .nickName(users.getNickName())
                .sumOfChargeAmount(sumOfChargeAmount)
                .email(users.getEmail())
                .build();
    }

}
