package org.example.hospitalmanagementsystem_securitylearning.Security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class WebSecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;
    private final OAuth2SuccessHandler oAuth2SuccessHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
        httpSecurity
                   .csrf(csrfConfig->csrfConfig.disable())
                   .sessionManagement(sessionConfig->sessionConfig.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                   .authorizeHttpRequests(auth->auth
                           .requestMatchers("/public/**","/auth/**").permitAll()
                                   .requestMatchers("/doctors/**").permitAll()
//                           .requestMatchers("/admin/**").hasRole("ADMIN")
//                           .requestMatchers("/doctors/**").hasAnyRole("ADMIN","DOCTOR")
                             .anyRequest().authenticated()
                   )
                   .oauth2Login(oAuth2->oAuth2
                           .failureHandler(
                           (request, response, exception) -> {
                               log.error("OAuth2 error: {}", exception.getMessage());
                           })
                           .successHandler(oAuth2SuccessHandler)
                   )
                   .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
//                   .formLogin(Customizer.withDefaults());
           return httpSecurity.build();
    }


}