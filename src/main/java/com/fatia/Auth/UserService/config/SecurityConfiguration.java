package com.fatia.Auth.UserService.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth ->
                        auth.requestMatchers(
                                        "/api/v1/auth/authenticate"
                                ).permitAll()
                                .requestMatchers(
                                        "/api/v1/auth/register"
                                ).denyAll()
                                .requestMatchers(
                                        "/v3/api-docs/**"
                                ).permitAll()
                                .requestMatchers(
                                        "/swagger-ui/**"
                                ).permitAll()
                                .requestMatchers(
                                        "/swagger-ui.html"
                                ).permitAll()
                                .requestMatchers(
                                        "/api/v1/users/get-user/",
                                        "/api/v1/users/register-manager",
                                        "/api/v1/users/register-worker",
                                        "/api/v1/users/update-user/",
                                        "/api/v1/users/delete-user/"
                                ).hasRole("ADMIN")
                                .requestMatchers(
                                        "/api/v1/users/get-user/",
                                        "/api/v1/users/register-worker",
                                        "/api/v1/users/update-user/",
                                        "/api/v1/users/delete-user/"
                                ).hasRole("MANAGER")
                                .anyRequest().authenticated())
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
