package com.study.deposit.domain.user.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.study.deposit.domain.user.domain.LoginType;
import com.study.deposit.domain.user.domain.Role;
import com.study.deposit.domain.user.domain.Users;
import com.study.deposit.global.common.exception.auth.AuthException;
import com.study.deposit.global.security.PrincipalDetails;
import java.util.Collections;
import java.util.HashMap;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {
    @Mock
    private SecurityContext securityContext;

    @Mock
    private Authentication authentication;
    @Mock
    private SecurityContextLogoutHandler logoutHandler;
    @InjectMocks
    private AuthService authService;

    @BeforeEach
    void setUp() {
        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    @DisplayName("인증된 유저 객체 성공")
    void GetUserWithAuthenticatedUser() {
        //given
        String kakaoEmail = "test@kakao.com";
        Users testUser = Users.builder()
                .nickName("test")
                .loginType(LoginType.KAKAO)
                .email(kakaoEmail)
                .role(Role.USER)
                .id(UUID.randomUUID()).build();

        PrincipalDetails principalDetails = new PrincipalDetails(testUser, new HashMap<>());
        Authentication authentication = new UsernamePasswordAuthenticationToken(principalDetails, null);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        //when
        Users result = authService.getUser();
        //then
        Assertions.assertThat(result.getEmail()).isEqualTo(testUser.getEmail());
    }

    @Test
    @DisplayName("인증되지 않은 유저")
    void GetUserWithAnonymousUser() {
        //given
        PrincipalDetails principalDetails = new PrincipalDetails(null, new HashMap<>());
        Authentication authentication = new AnonymousAuthenticationToken("key", principalDetails,
                principalDetails.getAuthorities());
        when(securityContext.getAuthentication()).thenReturn(authentication);

        //when,then
        assertThrows(AuthException.class, authService::getUser);
    }


    @Test
    @DisplayName("로그아웃 테스트")
    public void testLogout() {
        HttpServletRequest request = new MockHttpServletRequest();
        HttpServletResponse response = new MockHttpServletResponse();

        // Set up the SecurityContext with a mock Authentication
        SecurityContextHolder.setContext(securityContext);
        when(securityContext.getAuthentication()).thenReturn(authentication);

        // Call the logout method
        authService.logout(request, response);

        // Verify that the SecurityContextLogoutHandler was called with the mock Authentication
        verify(logoutHandler).logout(request, response, authentication);
    }
}