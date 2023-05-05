package com.study.deposit.domain.point.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.study.deposit.domain.point.domain.PaymentType;
import com.study.deposit.domain.point.domain.PointRecord;
import com.study.deposit.domain.point.dto.PointRecordCompleteDto;
import com.study.deposit.domain.point.dto.PointRecordPrepareDto;
import com.study.deposit.domain.point.dto.PointRecordResultDto;
import com.study.deposit.domain.point.service.IamPortService;
import com.study.deposit.domain.point.service.PointRecordService;
import com.study.deposit.domain.user.domain.LoginType;
import com.study.deposit.domain.user.domain.Role;
import com.study.deposit.domain.user.domain.Users;
import com.study.deposit.domain.user.service.AuthService;
import com.study.deposit.global.config.annotation.WithAuthUser;
import java.time.LocalDateTime;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(controllers = KaKaoPayController.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@MockBean(JpaMetamodelMappingContext.class)
class KaKaoPayControllerTest {
    private static final String TEST_TOKEN = "test_token";

    @Autowired
    MockMvc mockMvc;

    @MockBean
    IamPortService iamPortService;

    @MockBean
    PointRecordService pointRecordService;

    @MockBean
    AuthService authService;


    @Test
    @WithAuthUser(email = "test@naver.com", role = "ROLE_USER")
    @DisplayName("사전결제 API 테스트(성공)")
    void paymentPrepare() throws Exception {
        //given
        PointRecordPrepareDto testDto = getPointRecordPrepareDto();

        Users testUser = makeTestUser();

        String testToken = TEST_TOKEN;
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
        verify(pointRecordService).insertRecord(testUser, testDto, PaymentType.CHARGE);
    }

    private Users makeTestUser() {
        Users testUser = Users.builder()
                .nickName("test_user")
                .loginType(LoginType.KAKAO)
                .email("test@naver.com")
                .role(Role.USER)
                .id(UUID.randomUUID()).build();
        return testUser;
    }

    private PointRecordPrepareDto getPointRecordPrepareDto() {
        PointRecordPrepareDto testDto = new PointRecordPrepareDto();
        testDto.setAmount(1000L);
        testDto.setMerchant_uid(UUID.randomUUID().toString());
        return testDto;
    }

    @Test
    @WithAuthUser(email = "test@naver.com", role = "ROLE_USER")
    @DisplayName("결제 최종 완료 API 테스트[성공]")
    void paymentComplete_validPayment() throws Exception {
        // dto 생성
        PointRecordCompleteDto testDto = new PointRecordCompleteDto("test_imp_uid", "test_merchant_uid", "paid");

        // iamport test token
        String testToken = TEST_TOKEN;
        when(iamPortService.getToken()).thenReturn(testToken);

        //실제 iamport에서 기록된 결제 금액
        Long actualCharge = 1000L;
        PointRecordResultDto pointRecordResultDto = new PointRecordResultDto(actualCharge, "paid");
        when(iamPortService.paymentInfo(testDto.getImp_uid(), testToken)).thenReturn(pointRecordResultDto);

        PointRecord pointRecordInDb = PointRecord.builder()
                .merchant_uid(testDto.getMerchant_uid())
                .users(makeTestUser())
                .paymentDate(LocalDateTime.now())
                .chargeAmount(actualCharge)
                .paymentType(PaymentType.CHARGE)
                .build();

        when(pointRecordService.findByMerchantId(testDto.getMerchant_uid())).thenReturn(pointRecordInDb);
        when(iamPortService.validPayment(pointRecordResultDto.getChargeAmount(), pointRecordInDb)).thenReturn(true);

        //when
        mockMvc.perform(post("/api/v2/point/record/kakaopay/payment/complete")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(testDto)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        // Verify parameter
        verify(iamPortService).getToken();
        verify(iamPortService).paymentInfo(eq(testDto.getImp_uid()), eq(testToken));
        verify(pointRecordService).findByMerchantId(eq(testDto.getMerchant_uid()));
        verify(iamPortService).validPayment(eq(1000L), eq(pointRecordInDb));
        verify(pointRecordService, never()).deleteRecord(pointRecordInDb);
        verify(iamPortService, never()).rollbackPayment(any(), any(), any());
    }

    @Test
    @WithAuthUser(email = "test@naver.com", role = "ROLE_USER")
    @DisplayName("최종결제 확인 api테스트[성공]")
    public void paymentResult_shouldReturnOk() throws Exception {
        // Arrange
        PointRecordResultDto pointRecordResultDto = new PointRecordResultDto(1000L, "paid");
        String testImpUid = "test_imp_uid";
        when(iamPortService.getToken()).thenReturn(TEST_TOKEN);
        when(iamPortService.paymentInfo(testImpUid, TEST_TOKEN)).thenReturn(pointRecordResultDto);

        // Act & Assert
        mockMvc.perform(get("/api/v1/point/record/kakaopay/payment/result")
                        .with(csrf())
                        .param("imp_uid", testImpUid))
                .andExpect(status().isOk());
    }

    private String toJson(Object object) {
        try {
            return new ObjectMapper().writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}