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
package org.greenzone.service.register.activate.requestcode;

import java.util.Optional;

import org.greenzone.domain.user.User;
import org.greenzone.helper.hash.HashHelper;
import org.greenzone.repository.user.UserRepository;
import org.greenzone.service.email.EmailService;
import org.greenzone.service.register.activate.requestcode.RequestActivateCodeResponse.RequestActivateCodeResponseBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

/**
 * @author EN - Dec 18, 2024
 */
@Service
@RequiredArgsConstructor
public class ActivateRequestCodeServiceImpl implements ActivateRequestCodeService {

    private final EmailService emailService;
    private final HashHelper hashHelper;
    private final UserRepository userRepository;
    private final RequestActivateCodeRequestValidator requestActivateCodeRequestValidator;

    private void sendEmailActivation( User savedUser ) {

        emailService.sendActivationEmail( savedUser );
    }


    @Override
    public ResponseEntity<RequestActivateCodeResponse> requestCode(
            RequestActivateCodeRequest request ) {

        String email = request.getEmail();
        Optional<User> optionalUser = userRepository.findByEmail( email );
        Boolean emailNotRegistered = false;
        User user = null;

        if ( !optionalUser.isPresent() ) {

            emailNotRegistered = true;
        }
        else {
            user = optionalUser.get();
        }

        RequestActivateCodeResponseBuilder builder = RequestActivateCodeResponse.builder();

        HttpStatus httpStatus =
                requestActivateCodeRequestValidator.validate( builder, email, emailNotRegistered );

        RequestActivateCodeResponse response = builder.build();

        if ( !response.getHasValidationErrors() ) {

            user.setEmailActivationCode( hashHelper.getRandomHash() );
            userRepository.save( user );
            sendEmailActivation( user );
        }

        return ResponseEntity.status( httpStatus ).body( response );
    }
}
