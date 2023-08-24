package com.sparta.mdesigns.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.extras.springsecurity6.dialect.SpringSecurityDialect;

public class ThymeleafConfig {

    @Configuration
    public class ThymeleafConfigs {

        @Bean
        public SpringSecurityDialect springSecurityDialect(){

            return new SpringSecurityDialect();
        }
    }
}
