package com.study.deposit.global.security;

import com.study.deposit.domain.user.dao.UserDao;
import com.study.deposit.domain.user.domain.LoginType;
import com.study.deposit.domain.user.domain.Role;
import com.study.deposit.domain.user.domain.Users;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final UserDao userDao;


    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        //OAUTH 인증 서버(ex kakao, naver, google)
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint()
                .getUserNameAttributeName();

        OAuthAttributes attributes = OAuthAttributes.of(registrationId, userNameAttributeName,
                oAuth2User.getAttributes());

        Users authUser = doAuthentication(registrationId, attributes);

        return new PrincipalDetails(authUser, oAuth2User.getAttributes());
    }

    private Users doAuthentication(String registrationId, OAuthAttributes attributes) {
        Optional<Users> byEmail = userDao.findByEmail(attributes.getEmail());
        if (byEmail.isEmpty()) {
            return userDao.save(Users.builder()
                    .email(attributes.getEmail())
                    .nickName(attributes.getName())
                    .loginType(LoginType.findByProvider(registrationId))
                    .role(Role.USER)
                    .build());
        }
        return byEmail.get();
    }


}
