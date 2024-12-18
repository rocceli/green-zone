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
package org.greenzone.service.register.activate.validatecode;

import java.util.Arrays;
import java.util.Optional;

import org.greenzone.domain.token.Token;
import org.greenzone.domain.token.TokenType;
import org.greenzone.domain.user.User;
import org.greenzone.helper.jwt.JwtHelper;
import org.greenzone.helper.roles.RoleHelper;
import org.greenzone.repository.token.TokenRepository;
import org.greenzone.repository.user.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

/**
 * @author EN - Dec 18, 2024
 */
@Service
@RequiredArgsConstructor
public class ActivateValidateCodeServiceImpl implements ActivateValidateCodeService {

    private Logger logger = LoggerFactory.getLogger( ActivateValidateCodeServiceImpl.class );

    private final UserRepository userRepository;
    private final JwtHelper jwtHelper;
    private final TokenRepository tokenRepository;
    private final RoleHelper roleHelper;

    private void saveUserToken( User user, String jwtToken ) {

        var token = Token.builder()
                .user( user )
                .token( jwtToken )
                .tokenType( TokenType.BEARER )
                .expired( false )
                .revoked( false )
                .build();

        tokenRepository.save( token );
    }


    @Override
    @Transactional
    public ResponseEntity<ActivateValidateCodeResponse> validateCode(
            ActivateValidateCodeRequest validateCodeRequest ) {

        HttpStatus httpStatus = HttpStatus.OK;

        String emailActivationCode = validateCodeRequest.getEmailActivationCode();
        logger.info( "emailActivationCode: " + emailActivationCode );

        String jwtToken = null;
        String[] roles = null;

        if ( emailActivationCode != null ) {

            Optional<User> optionalUser =
                    userRepository.findByEmailActivationCode( emailActivationCode );

            User user = null;

            if ( !optionalUser.isPresent() ) {

                httpStatus = HttpStatus.UNPROCESSABLE_ENTITY;
            }
            else {
                user = optionalUser.get();
                user.setEmailActivationCode( null );
                user.setActive( Boolean.TRUE );
                user.setEnabled( Boolean.TRUE );
                User savedUser = userRepository.save( user );
                jwtToken = jwtHelper.generateToken( user );
                jwtHelper.revokeAllUserTokens( user );
                saveUserToken( savedUser, jwtToken );
                roles = roleHelper.getRoles( savedUser );
                logger.info( "roles: " + Arrays.toString( roles ) );
            }
        }

        ActivateValidateCodeResponse response =
                ActivateValidateCodeResponse
                        .builder()
                        .jwtToken( jwtToken )
                        .roles( roles )
                        .build();

        logger.info( response.toString() );

        return ResponseEntity.status( httpStatus ).body( response );
    }
}
