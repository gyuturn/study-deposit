package com.study.deposit.global.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    public static final String TITLE="DEPOSIT API";
    public static final String DESCRIPTION="DEPOSIT API명세서";
    public static final String VERSION="v0.0.1";
    private static final String BASIC_API_PATH="/api/v1";
    private static final String BASIC_API_NOT_AUTH_PATH="/api/v2";

    @Bean
    public GroupedOpenApi user() {
        return GroupedOpenApi.builder()
                .group("user")
                .pathsToMatch(BASIC_API_PATH+"/users/**")
                .build();
    }

    @Bean
    public GroupedOpenApi pointRecordKakaoPay() {
        return GroupedOpenApi.builder()
                .group("pointRecord-KakaoPay")
                .pathsToMatch(BASIC_API_NOT_AUTH_PATH+"/point/record/kakaopay/**",BASIC_API_PATH+"/point/record/kakaopay/**")
                .build();
    }
    @Bean
    public GroupedOpenApi auth() {
        return GroupedOpenApi.builder()
                .group("auth")
                .pathsToMatch(BASIC_API_PATH+"/auth/**")
                .build();
    }

    @Bean
    public GroupedOpenApi studyRoom() {
        return GroupedOpenApi.builder()
                .group("studyRoom")
                .pathsToMatch(BASIC_API_PATH+"/studyroom/**")
                .build();
    }

    @Bean
    public GroupedOpenApi hashTag() {
        return GroupedOpenApi.builder()
                .group("hashtag")
                .pathsToMatch(BASIC_API_PATH+"/hashtag/**")
                .build();
    }

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .addServersItem(new Server().url("/")) //swagger cors issue
                .info(new Info().title(TITLE)
                        .description(DESCRIPTION)
                        .version(VERSION));
    }
}