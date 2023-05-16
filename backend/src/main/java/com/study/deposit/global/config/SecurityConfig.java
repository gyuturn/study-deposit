package com.study.deposit.global.config;

import com.study.deposit.global.security.CustomOAuth2UserService;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomOAuth2UserService customOAuth2UserService;

    @Value("${front.login_redirect}") // application.yml 파일에서 frontendUrl 변수를 가져옴
    private String frontURI;
    private final static String frontRedirectURL = "/signin/check";

    @Bean
    public RedirectStrategy customRedirectStrategy() {
        return new DefaultRedirectStrategy() {
            @Override
            public void sendRedirect(HttpServletRequest request, HttpServletResponse response, String url)
                    throws IOException {
                String targetUrl = calculateRedirectUrl(request.getContextPath(), url);
                targetUrl = response.encodeRedirectURL(targetUrl);
                response.sendRedirect(frontURI + targetUrl); // 변경된 redirect URL 설정
            }
        };
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .formLogin().disable()
                .httpBasic().disable()
                .authorizeRequests()
                .antMatchers("/api/v1").permitAll()
                .and()
                .oauth2Login().userInfoEndpoint().userService(customOAuth2UserService)
                .and()
                .successHandler((request, response, authentication) -> {
                    // 로그인 성공 시 호출되는 핸들러
                    RedirectStrategy redirectStrategy = customRedirectStrategy();
                    redirectStrategy.sendRedirect(request, response, frontRedirectURL); // 로그인 성공 시 redirect할 URL 설정
                });
        return http.build();
    }
}
