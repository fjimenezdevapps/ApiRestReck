package com.fjdev.rackapirest.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.AllArgsConstructor;

@Configuration
@AllArgsConstructor
public class WebSecurityConfig {

        private final UserDetailsServiceImpl userDetailsService;
        private final JWTAuthorizationFilter jwtAuthorizationFilter;

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http, AuthenticationManager authManager)
                throws Exception {
    
            JWTAuthenticationFilter jwtAuthenticationFilter = new JWTAuthenticationFilter();
            jwtAuthenticationFilter.setAuthenticationManager(authManager);
            jwtAuthenticationFilter.setFilterProcessesUrl("/login");
    
            return http
                    .csrf(csrf -> csrf.disable())  // Deshabilita CSRF con Lambda DSL
                    .authorizeHttpRequests(auth -> auth
                            .anyRequest().authenticated()  // Requiere autenticación para cualquier solicitud
                    )
                    .sessionManagement(session -> session
                            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)  
                    )
                    .addFilter(jwtAuthenticationFilter)  
                    .addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class)
                    .build();  
        }


        @SuppressWarnings("removal")
        @Bean
        public AuthenticationManager authManager(HttpSecurity http)
                        throws Exception {
                return http.getSharedObject(AuthenticationManagerBuilder.class)
                                .userDetailsService(userDetailsService)
                                .passwordEncoder(passwordEncoder())
                                .and()
                                .build();
        }

        @Bean
        PasswordEncoder passwordEncoder() {
                return new BCryptPasswordEncoder();
        }
}
