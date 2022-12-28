package com.koreait.projectboard.config;


import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Optional;

@EnableJpaAuditing
@Configurable
public class JpaConfig {
    @Bean
    public AuditorAware<String> auditorAware(){//이거 완전 필수!!
        return () -> Optional.of("관리자");
        //스프링 시큐리티로 인증 기능을 붙이게 될 때 수정할거임
    }
}
