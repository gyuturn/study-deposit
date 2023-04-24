package com.study.deposit.domain.hashTag.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.study.deposit.domain.hashTag.dto.MakeHashTagReqDto;
import com.study.deposit.domain.hashTag.service.HashTagService;
import com.study.deposit.domain.point.controller.KaKaoPayController;
import com.study.deposit.domain.point.service.IamPortService;
import com.study.deposit.domain.point.service.PointRecordService;
import com.study.deposit.domain.user.service.AuthService;
import com.study.deposit.global.common.code.CommonCode;
import com.study.deposit.global.config.annotation.WithAuthUser;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

@WebMvcTest(controllers = HashTagController.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@MockBean(JpaMetamodelMappingContext.class)
class HashTagControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    HashTagService hashTagService;

    @Test
    @WithAuthUser(email = "test@naver.com", role = "ROLE_USER")
    @DisplayName("해시태그 생성 API")
    public void testCreateHashTag() throws Exception {
        String requestBody = "{\"tagName\":\"test\"}";

        doNothing().when(hashTagService).makeHashTag(ArgumentMatchers.any(MakeHashTagReqDto.class));

        mockMvc.perform(post("/hashtags")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isCreated());
    }
}