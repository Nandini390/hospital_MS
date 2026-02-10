package org.example.hospitalmanagementsystem_securitylearning.Security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@RequiredArgsConstructor
public class WebSecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
           httpSecurity
                   .csrf(csrfConfig->csrfConfig.disable())
                   .sessionManagement(sessionConfig->sessionConfig.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                   .authorizeHttpRequests(auth->auth
                           .requestMatchers("/public/**","/auth/**").permitAll()
                           .requestMatchers("/admin/**").hasRole("ADMIN")
                           .requestMatchers("/doctors/**").hasAnyRole("ADMIN","DOCTOR")
                   );
//                   .formLogin(Customizer.withDefaults());
           return httpSecurity.build();
    }


}