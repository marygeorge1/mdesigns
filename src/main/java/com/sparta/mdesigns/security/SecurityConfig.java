
package com.sparta.mdesigns.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.session.HttpSessionEventPublisher;

import javax.sql.DataSource;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http,MyAuthenticationSuccessHandler myAuthenticationSuccessHandler) throws Exception {

        http.authorizeHttpRequests((request) -> {
                    request.requestMatchers("/","/home","/images/*","/css/*","/showProduct/*","/display").permitAll()
                            .requestMatchers("/addToCart/*","/removeFromCart/*").authenticated();
                }).formLogin(form->form.loginPage("/showlogin")
                        .loginProcessingUrl("/authenticateTheUser")
                        /*.defaultSuccessUrl("/home")*/
                        .successHandler(myAuthenticationSuccessHandler)
                        .permitAll())
                .logout(logout->logout.permitAll().invalidateHttpSession(true));


        return http.build();
    }

  /*  @Bean
    JdbcUserDetailsManager userDetailsService(){
        return new JdbcUserDetailsManager();
    }*/

    @Bean
    public PasswordEncoder passwordEncoder(){
        return NoOpPasswordEncoder.getInstance();
    }

    @Bean
    public HttpSessionEventPublisher httpSessionEventPublisher(){
        return new HttpSessionEventPublisher();
    }

}


