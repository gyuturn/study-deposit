package com.study.deposit.domain.hashTag.domain;

import com.study.deposit.domain.hashTag.dto.MakeHashTagReqDto;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HashTag {
    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    private String tagName;

    public static HashTag toEntity(MakeHashTagReqDto dto) {
        return HashTag.builder()
                .tagName(dto.getTagName())
                .build();
    }

}
