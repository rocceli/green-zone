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
package org.greenzone.service.account.forgottenpassword.validatecode;

import java.util.Optional;

import org.greenzone.domain.user.User;
import org.greenzone.helper.string.StringHelper;
import org.greenzone.repository.user.UserRepository;
import org.greenzone.service.account.forgottenpassword.validatecode.ForgottenPasswordValidateCodeResponse.ForgottenPasswordValidateCodeResponseBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

/**
 * @author EN - Dec 18, 2024
 */
@Service
@RequiredArgsConstructor
public class ForgottenPasswordValidateCodeServiceImpl implements
        ForgottenPasswordValidateCodeService {

    private Logger logger = LoggerFactory.getLogger(
            ForgottenPasswordValidateCodeServiceImpl.class );

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final StringHelper stringHelper;
    private final ForgottenPasswordValidateCodeRequestValidator validator;

    @Value( "${password.validation.minimum-length}" )
    private Integer passwordMinimumLength;

    @Value( "${password.validation.needs-mixed-case}" )
    private Boolean passwordNeedsMixedCase;

    @Value( "${password.validation.needs-special-characters}" )
    private Boolean passwordNeedsSpecialCharacters;

    @Value( "${password.validation.needs-numbers}" )
    private Boolean passwordNeedsNumbers;

    @Value( "${password.validation.needs-letters}" )
    private Boolean passwordNeedsLetters;

    @Override
    @Transactional
    public ResponseEntity<ForgottenPasswordValidateCodeResponse> validate(
            ForgottenPasswordValidateCodeRequest request ) {

        ForgottenPasswordValidateCodeResponseBuilder builder =
                ForgottenPasswordValidateCodeResponse.builder();

        String password = stringHelper.trim( request.getPassword() );
        String passwordAlt = stringHelper.trim( request.getPasswordAlt() );
        String forgottenPasswordCode = request.getCode();

        boolean userNotFound = false;
        User user = null;

        Optional<User> optionalUser =
                userRepository.findByForgottenPasswordCode( forgottenPasswordCode );

        if ( !optionalUser.isPresent() ) {

            userNotFound = true;
        }
        else {
            user = optionalUser.get();
        }

        HttpStatus httpStatus =
                validator.validate(
                        builder,
                        userNotFound,
                        password, passwordAlt );

        ForgottenPasswordValidateCodeResponse response = builder.build();

        if ( !response.getHasValidationErrors() ) {

            user.setForgottenPasswordCode( null );
            String passwordHash = passwordEncoder.encode( password );
            logger.info( "@@@@ passwordHash: " + passwordHash );
            user.setPasswordHash( passwordHash );
            userRepository.save( user );
        }

        return ResponseEntity.status( httpStatus ).body( response );
    }


    public static void main( String[] args ) {

        PasswordEncoder passowrdEncoder = new BCryptPasswordEncoder();
        System.out.println( passowrdEncoder.encode( "a123AB$#aaaab" ) );
        System.out.println( passowrdEncoder.encode( "a123AB$#aaaab" ) );
    }
}
