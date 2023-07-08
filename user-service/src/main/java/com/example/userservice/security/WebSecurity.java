package com.example.userservice.security;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurity {

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()

                .authorizeRequests()
                .antMatchers("/**").permitAll()
                .requestMatchers(PathRequest.toH2Console()).permitAll()     //h2-console 접속 가능하게 설정
                .anyRequest().authenticated()

                //h2-console 하위 모든 요청들과 파비콘 관련 요청은 Spring Security 로직을 수행하지 않도록 설정
                .and()
                .headers()
                .frameOptions()
                .sameOrigin();

        return http.build();
    }
}
