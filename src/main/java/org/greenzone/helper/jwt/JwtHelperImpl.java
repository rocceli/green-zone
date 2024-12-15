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

import java.security.Key;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

import org.greenzone.domain.token.Token;
import org.greenzone.domain.token.TokenType;
import org.greenzone.domain.user.Role;
import org.greenzone.domain.user.User;
import org.greenzone.repository.token.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

/**
 * @author EN - Dec 15, 2024
 */
@Component
public class JwtHelperImpl implements JwtHelper {

    @Value( "${jwt.life-in-milliseconds}" )
    private Long lifeInMilliSeconds;

    private static final String SECRET_KEY =
            "404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970";

    @Autowired
    private TokenRepository tokenRepository;

    private boolean isTokenExpired( String token ) {

        return extractExpiration( token ).before( new Date() );
    }


    private Date extractExpiration( String token ) {

        return extractClaim( token, Claims::getExpiration );
    }


    private Claims extractAllClaims( String token ) {

        return Jwts
                .parserBuilder()
                .setSigningKey( getSignInKey() )
                .build()
                .parseClaimsJws( token )
                .getBody();
    }


    private Key getSignInKey() {

        byte[] keyBytes = Decoders.BASE64.decode( SECRET_KEY );
        return Keys.hmacShaKeyFor( keyBytes );
    }


    @Override
    public String extractUsername( String token ) {

        return extractClaim( token, Claims::getSubject );
    }


    @Override
    public <T> T extractClaim( String token, Function<Claims, T> claimsResolver ) {

        final Claims claims = extractAllClaims( token );
        return claimsResolver.apply( claims );
    }


    @Override
    public String generateToken( User user ) {

        return generateToken( new HashMap<>(), user );
    }


    @Override
    public String generateToken(
            Map<String, Object> extraClaims,
            User user ) {

        Long nanoTime = System.nanoTime();
        extraClaims.put( "nanoTime", nanoTime );

        Set<Role> roles = user.getRoles();

        List<String> roleString = new ArrayList<>();

        for ( Role role : roles ) {

            roleString.add( role.getName() );
        }

        extraClaims.put( "roles", roleString );
        String userName = user.getEmail();

        return Jwts
                .builder()
                .setClaims( extraClaims )
                .setSubject( userName )
                .setIssuedAt( new Date( System.currentTimeMillis() ) )
                .setExpiration( new Date( System.currentTimeMillis() + lifeInMilliSeconds ) )
                .signWith( getSignInKey(), SignatureAlgorithm.HS256 )
                .compact();
    }


    @Override
    public boolean isTokenValid( String token, UserDetails userDetails ) {

        final String usernameFromToken = extractUsername( token );
        String usernameFromUserDetails = userDetails.getUsername();
        Boolean usernamesEqual = usernameFromToken.equals( usernameFromUserDetails );
        Boolean tokenExpired = isTokenExpired( token );
        Boolean valid = usernamesEqual && !tokenExpired;
        return valid;
        // return ( username.equals( userDetails.getUsername() ) ) && !isTokenExpired( token );
    }


    @Override
    public void revokeAllUserTokens( User user ) {

        var validUserTokens = tokenRepository.findAllValidTokenByUser( user.getId() );

        if ( validUserTokens.isEmpty() )
            return;

        validUserTokens.forEach( token -> {
            token.setExpired( true );
            token.setRevoked( true );
        } );

        tokenRepository.saveAll( validUserTokens );
    }


    @Override
    public void saveUserToken( User user, String jwtToken ) {

        var token = Token.builder()
                .user( user )
                .token( jwtToken )
                .tokenType( TokenType.BEARER )
                .expired( false )
                .revoked( false )
                .build();

        tokenRepository.save( token );
    }


    public static void main( String[] args ) throws Exception {

        JwtHelperImpl jwtHelper = new JwtHelperImpl();
        jwtHelper.lifeInMilliSeconds = 900000000000l;

        System.out.println( "################################" );
        String email = "jd@gmail.com";
        System.out.println( email );
        Set<Role> roles = new HashSet<>();
        roles.add( Role.builder().name( "PLEDGER" ).build() );
        User user = User.builder().roles( roles ).email( email ).build();
        System.out.println( jwtHelper.generateToken( user ) );

        /*
        System.out.println( "################################" );
        email = "en+admin@gmail.com";
        System.out.println( email );
        System.out.println( jwtHelper.generateToken( email ) );
        
        System.out.println( "################################" );
        email = "en+employer@gmail.com";
        System.out.println( email );
        System.out.println( jwtHelper.generateToken( email ) );
        */
    }
}
