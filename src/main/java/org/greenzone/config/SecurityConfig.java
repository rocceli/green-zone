/*
    ================================================================================================
    This code is part of the Greenzone software.

    GreenZone is a closed source software of EN (Eliah Ngugi) which is owned by EN.
    
        Elijah Ngugi Gachuki
    
    This software is private closed source software.
    
    For further details look at or request greenzone-license.txt for further details.

    Copyright (C) 2024 

    Email:  elijah.ngugi.gachuki@gmail.com
    Domain: N/A

    ================================================================================================
    Author : EN
    ================================================================================================
 */
package org.greenzone.config;

import org.greenzone.filter.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import lombok.RequiredArgsConstructor;

/**
 * @author EN - Dec 15, 2024
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity( prePostEnabled = true )
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;
    private final LogoutHandler logoutHandler;

    @Bean
    SecurityFilterChain securityFilterChain( HttpSecurity http ) throws Exception {

        http
                .csrf(
                        csrf -> csrf.disable() )

                .authorizeHttpRequests(
                        authorizeHttpRequests -> authorizeHttpRequests.requestMatchers(
                                "/api/v1/account/activate/requestcode",
                                "/api/v1/account/activate/validatecode",
                                "/api/v1/account/forgottenpassword/requestcode",
                                "/api/v1/account/forgottenpassword/validate",
                                "/api/v1/login",
                                "/api/v1/logout",
                                "/api/v1/image/avatar/*",
                                "/api/v1/register/initialdata",
                                "/api/v1/initialdata",
                                "/api/v1/register/save",
                                "/error" )
                                .permitAll()
                                .requestMatchers( "/api/v1/*" )
                                .authenticated()
                                .anyRequest()
                                .authenticated() )

                .sessionManagement(
                        sessionManagement -> sessionManagement
                                .sessionCreationPolicy(
                                        SessionCreationPolicy.STATELESS ) )

                .authenticationProvider( authenticationProvider )

                .addFilterBefore( jwtAuthFilter, UsernamePasswordAuthenticationFilter.class )

                .logout(
                        logout -> logout
                                .logoutUrl( "/api/v1/auth/logout" )
                                .addLogoutHandler( logoutHandler )
                                .logoutSuccessHandler(
                                        ( request, response,
                                                authentication ) -> SecurityContextHolder
                                                        .clearContext() ) );

        return http.build();
    }
}
