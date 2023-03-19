package com.study.deposit.domain.user.service;

import com.study.deposit.domain.user.domain.Users;
import com.study.deposit.global.common.code.auth.AuthErrorCode;
import com.study.deposit.global.common.exception.auth.AuthException;
import com.study.deposit.global.security.PrincipalDetails;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthService {
    private static final String COOKIE_NAME = "JSESSIONID";
    private final SecurityContextLogoutHandler logoutHandler;


    public Users getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication instanceof AnonymousAuthenticationToken) {
            log.error("User in spring security is anonymous");
            throw new AuthException(AuthErrorCode.GUEST_USER, HttpStatus.UNAUTHORIZED);
        }
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        log.info("User in spring security 조회:{}", principalDetails.getUser().getId());
        return principalDetails.getUser();
    }

    public void logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            //인증정보가 존재하는경우(로그인 되어 있는경우)
            removeCookie(response);
            log.info("cookie삭제 완료");
            logoutHandler.logout(request, response, authentication);
            log.info("security session 삭제 완료");
        }
    }

    private void removeCookie(HttpServletResponse response) {
        List<Cookie> cookieList = new ArrayList<>();
        cookieList.add(new Cookie(COOKIE_NAME, null));

        for(Cookie cookie : cookieList) {
            cookie.setMaxAge(0);
            cookie.setPath("/");
            response.addCookie(cookie);
        }
    }
}
