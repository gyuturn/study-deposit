package com.study.deposit;

import java.util.TimeZone;
import javax.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
public class DepositApplication {
    @PostConstruct
    public void started() {
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"));
    }



    @Configuration
    @EnableJpaAuditing // JPA Auditing 활성화
    public class JpaConfig {
    }

    public static void main(String[] args) {
        SpringApplication.run(DepositApplication.class, args);
    }

}
