package com.studentportal.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

   @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
       http
           .csrf(csrf -> csrf.disable())
           .headers(headers -> headers.frameOptions(frame -> frame.disable())) // Needed for H2
            .authorizeHttpRequests(auth -> auth
               .requestMatchers("/api/auth/**", "/h2/**", "/h2-console/**").permitAll()
                .anyRequest().authenticated()
            );
       return http.build(); // No formLogin(), no httpBasic()
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
       return new BCryptPasswordEncoder();
    }
}

