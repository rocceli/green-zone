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
package org.greenzone.service.logout;

import org.greenzone.repository.token.TokenRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

/**
 * @author EN - Dec 15, 2024
 */
@Service
@RequiredArgsConstructor
public class LogoutService implements LogoutHandler {

    private Logger logger = LoggerFactory.getLogger( LogoutService.class );

    private final TokenRepository tokenRepository;

    @Override
    public void logout(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication ) {

        logger.info( "Logging out" );

        final String authHeader = request.getHeader( "Authorization" );
        final String jwt;

        if ( authHeader == null || !authHeader.startsWith( "Bearer " ) ) {

            return;
        }

        jwt = authHeader.substring( 7 );
        var storedToken = tokenRepository.findByToken( jwt ).orElse( null );

        if ( storedToken != null ) {

            storedToken.setExpired( true );
            storedToken.setRevoked( true );
            tokenRepository.save( storedToken );
            SecurityContextHolder.clearContext();
        }
    }
}
