package com.study.deposit.domain.point.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.study.deposit.domain.point.dto.PointRecordPrepareDto;
import com.study.deposit.domain.point.service.IamPortService;
import com.study.deposit.domain.point.service.PointRecordService;
import com.study.deposit.domain.user.controller.UsersController;
import com.study.deposit.domain.user.domain.LoginType;
import com.study.deposit.domain.user.domain.Role;
import com.study.deposit.domain.user.domain.Users;
import com.study.deposit.domain.user.service.AuthService;
import com.study.deposit.global.config.annotation.WithAuthUser;
import java.io.IOException;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(controllers = KaKaoPayController.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@MockBean(JpaMetamodelMappingContext.class)
class KaKaoPayControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    IamPortService iamPortService;

    @MockBean
    PointRecordService pointRecordService;

    @MockBean
    AuthService authService;


    @Test
    @WithAuthUser(email = "test@naver.com",role = "ROLE_USER")
    @DisplayName("사전결제 API 테스트(성공)")
    void paymentPrepare() throws Exception {
        //given
        PointRecordPrepareDto testDto = new PointRecordPrepareDto();
        testDto.setAmount(1000L);
        testDto.setMerchant_uid(UUID.randomUUID().toString());

        Users testUser = Users.builder()
                .nickName("test_user")
                .loginType(LoginType.KAKAO)
                .email("test@naver.com")
                .role(Role.USER)
                .id(UUID.randomUUID()).build();

        String testToken = "test_token";
        when(iamPortService.getToken()).thenReturn(testToken);
        when(authService.getUser()).thenReturn(testUser);

        //when
        mockMvc.perform(post("/api/v1/point/record/kakaopay/payment/prepare")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(testDto)))
                .andExpect(status().isOk())
                .andReturn();

        //verify parameter
        verify(iamPortService).getToken();
        verify(iamPortService).paymentPrepare(testDto, testToken);
        verify(pointRecordService).insertRecord(testUser, testDto);
    }

    private String toJson(Object object) {
        try {
            return new ObjectMapper().writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}