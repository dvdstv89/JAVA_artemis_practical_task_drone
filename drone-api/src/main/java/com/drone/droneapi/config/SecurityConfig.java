package com.drone.droneapi.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
//@EnableWebSecurity
public class SecurityConfig {

    private static final String API_URL_PATTERN = "/v1/**";

    @Value("${spring.h2.console.path}")
    private String h2ConsolePath;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
       /* http
                .authorizeHttpRequests(auth -> auth
                        //.requestMatchers(AntPathRequestMatcher.antMatcher(h2ConsolePath)).permitAll()
                                .anyRequest().permitAll()
                       // .anyRequest().authenticated()
                );
             //   .headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin))
              //  .csrf(csrf -> csrf
              //          .ignoringRequestMatchers(AntPathRequestMatcher.antMatcher(h2ConsolePath)))
           //     .httpBasic(Customizer.withDefaults())
             //   .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
               //         .invalidSessionUrl("/v1/")
           //     );

        return http.build();*/

        return http.csrf(csrf -> csrf.disable()).build();
    }
}
