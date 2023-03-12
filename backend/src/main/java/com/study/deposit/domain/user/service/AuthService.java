package com.study.deposit.domain.user.service;

import com.study.deposit.domain.user.domain.Users;
import com.study.deposit.global.common.code.auth.AuthErrorCode;
import com.study.deposit.global.common.exception.auth.AuthException;
import com.study.deposit.global.security.PrincipalDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AuthService {

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
}
