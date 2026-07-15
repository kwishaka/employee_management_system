package com.ems.mis.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authorizeHttpRequests(auth -> auth

                        // Feature 1
                        .requestMatchers("/api/applications/submit").permitAll()

                        // Feature 2
                        .requestMatchers("/api/applications/track/**").permitAll()

                        // Uploaded files
                        .requestMatchers("/uploads/**").permitAll()

                        // Everything else
                        .anyRequest().authenticated()
                );

        return http.build();
    }
}