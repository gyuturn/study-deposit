package com.study.deposit.domain.user.dto;

import com.study.deposit.domain.user.domain.Users;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResDto {
    private String nickName;
    private String email;

    public static UserResDto makeDto(Users users) {
        return UserResDto.builder()
                .nickName(users.getNickName())
                .email(users.getEmail()).build();
    }
}
