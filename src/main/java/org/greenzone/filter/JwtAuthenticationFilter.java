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
package org.greenzone.filter;

import java.io.IOException;

import org.greenzone.helper.jwt.JwtHelper;
import org.greenzone.repository.token.TokenRepository;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

/**
 * @author EN - Dec 15, 2024
 */
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtHelper jwtHelper;
    private final UserDetailsService userDetailsService;
    private final TokenRepository tokenRepository;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain ) throws ServletException, IOException {

        final String authHeader = request.getHeader( "Authorization" );
        final String jwt;
        final String userEmail;

        if ( authHeader == null || !authHeader.startsWith( "Bearer " ) ) {
            filterChain.doFilter( request, response );
            return;
        }

        // here we have a bearer 
        jwt = authHeader.substring( 7 );

        try {
            userEmail = jwtHelper.extractUsername( jwt );

            if ( userEmail != null && SecurityContextHolder.getContext()
                    .getAuthentication() == null ) {

                UserDetails userDetails = this.userDetailsService.loadUserByUsername( userEmail );

                var isTokenValid = tokenRepository.findByToken( jwt )
                        .map( t -> !t.isExpired() && !t.isRevoked() )
                        .orElse( false );

                if ( jwtHelper.isTokenValid( jwt, userDetails ) && isTokenValid ) {

                    UsernamePasswordAuthenticationToken authToken =
                            new UsernamePasswordAuthenticationToken(
                                    userDetails,
                                    null,
                                    userDetails.getAuthorities() );

                    authToken.setDetails(
                            new WebAuthenticationDetailsSource().buildDetails( request ) );

                    SecurityContextHolder.getContext().setAuthentication( authToken );
                }
            }
        }
        catch ( ExpiredJwtException e ) {

            response.setHeader( "x-error-type", "JWT_EXPIRED" );
        }

        filterChain.doFilter( request, response );
    }
}
