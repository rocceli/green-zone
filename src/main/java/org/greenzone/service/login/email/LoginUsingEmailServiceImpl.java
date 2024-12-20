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
package org.greenzone.service.login.email;

import java.util.Optional;

import org.greenzone.domain.user.User;
import org.greenzone.helper.hash.HashHelper;
import org.greenzone.helper.jwt.JwtHelper;
import org.greenzone.helper.roles.RoleHelper;
import org.greenzone.helper.string.StringHelper;
import org.greenzone.repository.user.UserRepository;
import org.greenzone.service.email.EmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

/**
 * @author EN - Dec 16, 2024
 */
@Service
@RequiredArgsConstructor
public class LoginUsingEmailServiceImpl implements LoginUsingEmailService {

    private Logger logger = LoggerFactory.getLogger( LoginUsingEmailServiceImpl.class );

    private final UserRepository userRepository;
    private final JwtHelper jwtHelper;
    private final AuthenticationManager authenticationManager;
    private final StringHelper stringHelper;
    private final EmailService emailService;
    private final HashHelper hashHelper;
    private final RoleHelper roleHelper;

    @Value( "${first-name.validation.minimum-length}" )
    private Integer lengthMinimumFirstName;

    @Value( "${last-name.validation.minimum-length}" )
    private Integer lengthMinimumLastName;

    @Value( "${username.validation.minimum-length}" )
    private Integer lengthMinimumUsername;

    private void sendEmailActivation( User savedUser ) {

        emailService.sendActivationEmail( savedUser );
    }


    @Transactional
    public ResponseEntity<LoginResponse> authenticate( LoginRequest request ) {

        String email = stringHelper.trimAndLowerCase( request.getEmail() );
        String password = stringHelper.trim( request.getPassword() );
        Optional<User> optionalUser = userRepository.findByEmail( email );
        String jwtToken = null;
        String userName = optionalUser.get().getUsername();
        Long id = optionalUser.get().getId();
        Boolean needsValidateEmailActivationCode = Boolean.FALSE;
        HttpStatus httpStatus = HttpStatus.OK;
        String[] roles = {};

        if ( !optionalUser.isPresent() ) {

            httpStatus = HttpStatus.UNAUTHORIZED;
        }
        else {

            User user = optionalUser.get();
            roles = roleHelper.getRoles( user );

            if ( !user.getActive() ) {

                needsValidateEmailActivationCode = Boolean.TRUE;
                user.setEmailActivationCode( hashHelper.getRandomHash() );
                User savedUser = userRepository.save( user );
                sendEmailActivation( savedUser );
                httpStatus = HttpStatus.FORBIDDEN;

                HttpHeaders responseHeaders = new HttpHeaders();
                responseHeaders.set( "403-reason", "Awaiting Email Activation" );

                return ResponseEntity
                        .status( httpStatus )
                        .headers( responseHeaders ).build();

            }
            else {

                try {
                    authenticationManager.authenticate(
                            new UsernamePasswordAuthenticationToken( email, password ) );
                }
                catch ( AuthenticationException e ) {

                    HttpHeaders responseHeaders = new HttpHeaders();
                    responseHeaders.set( "401-reason", "Incorrect Credentials" );

                    return ResponseEntity
                            .status( HttpStatus.UNAUTHORIZED )
                            .headers( responseHeaders ).build();

                    // throw new ResponseStatusException(
                    //         HttpStatus.UNAUTHORIZED, "Incorrect email or password" );
                }

                jwtToken = jwtHelper.generateToken( user );
                jwtHelper.revokeAllUserTokens( user );
                jwtHelper.saveUserToken( user, jwtToken );
            }
        }

        LoginResponse response =
                LoginResponse.builder()
                        .token( jwtToken )
                        .needsValidateEmailActivationCode( needsValidateEmailActivationCode )
                        .roles( roles )
                        .id( id )
                        .username( userName )
                        .build();

        return ResponseEntity
                .status( httpStatus )
                .body( response );
    }
}
