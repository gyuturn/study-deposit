package com.study.deposit.domain.user.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.study.deposit.domain.user.domain.LoginType;
import com.study.deposit.domain.user.domain.Role;
import com.study.deposit.domain.user.domain.Users;
import com.study.deposit.global.common.exception.auth.AuthException;
import com.study.deposit.global.security.PrincipalDetails;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
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
    SecurityContext securityContext;

    @Mock
    Authentication authentication;

    @InjectMocks
    private AuthService authService;

    @Mock
    HttpServletRequest request;

    @Mock
    HttpServletResponse response;

    @Mock
    SecurityContextLogoutHandler handler;

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


//    @Test
//    @DisplayName("로그아웃 테스트")
//    public void testLogout() {
//        //given
//        Authentication auth = mock(Authentication.class);
//        when(securityContext.getAuthentication()).thenReturn(auth);
//        SecurityContextHolder.setContext(securityContext);
//
//        Cookie cookie = new Cookie("JSESSIONID", "sessionValue");
//        when(request.getCookies()).thenReturn(new Cookie[] {cookie});
//
//        //when
//        authService.logout(request, response);
//
//        //then
//        verify(handler, times(1)).logout(request, response, auth);
//        verify(response, times(1)).addCookie(any(Cookie.class));
//        verify(cookie, times(1)).setMaxAge(0);
//        verify(cookie, times(1)).setPath("/");
//    }
}