package com.study.deposit.domain.hashTag.domain;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import com.study.deposit.domain.hashTag.dto.MakeHashTagReqDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class HashTagTest {

    @Test
    @DisplayName("해시태그 생성 dto -> HashTag 객체 변환")
    void toEntity() {
        //given
        MakeHashTagReqDto dto = new MakeHashTagReqDto();
        dto.setTagName("testTag");
        //when
        HashTag hashTag = HashTag.toEntity(dto);
        //then
        assertThat(dto.getTagName()).isEqualTo(hashTag.getTagName());
    }
}