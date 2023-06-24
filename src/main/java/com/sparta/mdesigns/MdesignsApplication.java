package com.sparta.mdesigns;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Description;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

@SpringBootApplication
public class MdesignsApplication {

    public static void main(String[] args) {

        SpringApplication.run(MdesignsApplication.class, args);
    }

    @Bean
    @Description("Thymeleaf template resolver serving HTML 5 emails")
    public ClassLoaderTemplateResolver thymeleafTemplateResolver() {

        ClassLoaderTemplateResolver thymeleafTemplateResolver = new ClassLoaderTemplateResolver();
          thymeleafTemplateResolver.setPrefix("templates/");
          thymeleafTemplateResolver.setSuffix(".html");

        //thymeleafTemplateResolver.setTemplateMode("HTML5");
        //emailTemplateResolver.setCharacterEncoding(CharEncoding.UTF_8);
        //thymeleafTemplateResolver.setOrder(1);
        return thymeleafTemplateResolver;
    }

}
