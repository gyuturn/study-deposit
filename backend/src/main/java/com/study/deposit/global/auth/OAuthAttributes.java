package com.study.deposit.global.auth;

import com.study.deposit.domain.user.domain.LoginType;
import java.util.Map;
import lombok.Builder;
import lombok.Getter;

@Builder
public class OAuthAttributes {
    private Map<String, Object> attributes;
    private String nameAttributeKey;
    private String provider;
    private String name;
    private String email;

    // 해당 로그인인 서비스가 kakao인지 google인지 구분하여, 알맞게 매핑을 해주도록 합니다.
    // 여기서 registrationId는 OAuth2 로그인을 처리한 서비스 명("google","kakao","naver"..)이 되고,
    // userNameAttributeName은 해당 서비스의 map의 키값이 되는 값이됩니다. {google="sub", kakao="id", naver="response"}
    public static OAuthAttributes of(String registrationId, String userNameAttributeName,
                                     Map<String, Object> attributes) {
        if (LoginType.KAKAO.getProvider().equals(registrationId)) {
            return ofKakao(userNameAttributeName, attributes);
        }

        return ofKakao(userNameAttributeName, attributes);
    }


    private static OAuthAttributes ofKakao(String userNameAttributeName, Map<String, Object> attributes) {
        Map<String, Object> kakao_account = (Map<String, Object>) attributes.get("kakao_account");  // 카카오로 받은 데이터에서 계정 정보가 담긴 kakao_account 값을 꺼낸다.
        Map<String, Object> profile = (Map<String, Object>) kakao_account.get("profile");   // 마찬가지로 profile(nickname, image_url.. 등) 정보가 담긴 값을 꺼낸다.

        return OAuthAttributes.builder()
                .name((String) profile.get("nickname"))
                .email((String) kakao_account.get("email"))
                .attributes(attributes)
                .provider(LoginType.KAKAO.getProvider())
                .nameAttributeKey(userNameAttributeName)
                .build();
    }

    public Map<String, Object> getAttributes() {
        return attributes;
    }

    public String getNameAttributeKey() {
        return nameAttributeKey;
    }

    public String getProvider() {
        return provider;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
}
