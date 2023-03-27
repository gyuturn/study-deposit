package com.study.deposit.domain.user.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.study.deposit.domain.user.domain.LoginType;
import com.study.deposit.domain.user.domain.Role;
import com.study.deposit.domain.user.domain.Users;
import com.study.deposit.domain.user.dto.NickNameReqDto;
import com.study.deposit.domain.user.service.AuthService;
import com.study.deposit.domain.user.service.UserService;
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
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(controllers = UsersController.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@MockBean(JpaMetamodelMappingContext.class)
class UsersControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    UserService usersService;

    @MockBean
    AuthService authService;


    @Test
    @WithMockUser
    @DisplayName("유저 이름 변경 api")
    public void testUpdateNickname() throws Exception {
        //given
        String before = "before";
        Users beforeUser = Users.builder()
                .nickName(before)
                .loginType(LoginType.KAKAO)
                .email("test@naver.com")
                .role(Role.USER)
                .id(UUID.randomUUID()).build();

        String after = "after";
        NickNameReqDto nickNameReqDto = new NickNameReqDto();
        nickNameReqDto.setNewNickName(after);

        //when
        when(authService.getUser()).thenReturn(beforeUser);
        doNothing().when(usersService).updateNickName(any(), any());

        //then
        MvcResult result = mockMvc.perform(
                        patch("/api/v1/users/nickname")
                                .with(csrf())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(toJson(nickNameReqDto)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        verify(usersService, times(1)).updateNickName(beforeUser, after);
        verify(authService, times(1)).getUser();
    }


    private String toJson(Object object) {
        try {
            return new ObjectMapper().writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}