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
package org.greenzone.service.account.forgottenpassword.requestcode;

import java.util.Optional;

import org.greenzone.domain.user.User;
import org.greenzone.helper.hash.HashHelper;
import org.greenzone.helper.string.StringHelper;
import org.greenzone.repository.user.UserRepository;
import org.greenzone.service.account.forgottenpassword.requestcode.ForgottenPasswordRequestCodeResponse.ForgottenPasswordRequestCodeResponseBuilder;
import org.greenzone.service.email.EmailService;
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
public class ForgottenPasswordRequestCodeServiceImpl implements
        ForgottenPasswordRequestCodeService {

    private final UserRepository userRepository;
    private final HashHelper hashHelper;
    private final EmailService emailService;
    private final ForgottenPasswordRequestCodeRequestValidator validator;
    private final StringHelper stringHelper;

    @Override
    @Transactional
    public ResponseEntity<ForgottenPasswordRequestCodeResponse> sendForgottenPasswordEmail(
            ForgottenPasswordRequestCodeRequest request ) {

        String email = stringHelper.trimAndLowerCase( request.getEmail() );
        Optional<User> optionalUser = userRepository.findByEmail( email );
        Boolean cannotFindRegisteredEmail = false;

        if ( !optionalUser.isPresent() ) {

            cannotFindRegisteredEmail = true;
        }

        ForgottenPasswordRequestCodeResponseBuilder builder =
                ForgottenPasswordRequestCodeResponse.builder();

        HttpStatus httpStatus = validator.validate( builder, cannotFindRegisteredEmail );
        ForgottenPasswordRequestCodeResponse response = builder.build();

        if ( !response.getHasValidationErrors() ) {

            User user = optionalUser.get();

            String randomHash = hashHelper.getRandomHash();
            user.setForgottenPasswordCode( randomHash );
            userRepository.save( user );
            emailService.sendForgottenPasswordEmail( user );
        }

        return ResponseEntity.status( httpStatus ).body( response );
    }
}
