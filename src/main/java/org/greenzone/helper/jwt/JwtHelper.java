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
package org.greenzone.helper.jwt;

import java.util.Map;
import java.util.function.Function;

import org.greenzone.domain.user.User;
import org.springframework.security.core.userdetails.UserDetails;

import io.jsonwebtoken.Claims;

/**
 * @author EN - Dec 15, 2024
 */
public interface JwtHelper {

    String extractUsername( String token );


    <T> T extractClaim( String token, Function<Claims, T> claimsResolver );


    String generateToken( User user );


    String generateToken(
            Map<String, Object> extraClaims,
            User user );


    boolean isTokenValid( String token, UserDetails userDetails );


    void revokeAllUserTokens( User user );


    void saveUserToken( User user, String jwtToken );
}
