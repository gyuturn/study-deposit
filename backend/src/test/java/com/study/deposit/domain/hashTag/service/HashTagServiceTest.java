package com.study.deposit.domain.hashTag.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

import com.study.deposit.domain.hashTag.dao.HashTagDao;
import com.study.deposit.domain.hashTag.domain.HashTag;
import com.study.deposit.domain.hashTag.dto.MakeHashTagReqDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class HashTagServiceTest {
    @Mock
    private HashTagDao hashTagDao;

    @InjectMocks
    private HashTagService hashTagService;

    @Test
    @DisplayName("해시태그 생성 로직")
    void makeHashTag() {
        MakeHashTagReqDto dto = new MakeHashTagReqDto();
        dto.setTagName("hashTag");

        hashTagService.makeHashTag(dto);

        verify(hashTagDao).save(any(HashTag.class));
    }
}