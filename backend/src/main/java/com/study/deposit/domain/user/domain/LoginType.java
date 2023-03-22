package com.study.deposit.domain.user.domain;

import java.util.Arrays;
import java.util.Collections;
import lombok.Getter;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;

@Getter
public enum LoginType {
    KAKAO("kakao"),
    ;

    private String provider;

    LoginType(String provider) {
        this.provider = provider;
    }

    //TODO 예외처리
    public static LoginType findByProvider(String OAuthProvider) {
        return Arrays.stream(values())
                .filter(value -> value.provider.equals(OAuthProvider))
                .findAny()
                .orElse(LoginType.KAKAO);
    }
}
