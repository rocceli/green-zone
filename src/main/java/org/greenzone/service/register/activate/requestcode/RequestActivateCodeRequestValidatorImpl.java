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

import org.greenzone.helper.email.EmailHelper;
import org.greenzone.service.register.activate.requestcode.RequestActivateCodeResponse.RequestActivateCodeResponseBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

/**
 * @author EN - Dec 18, 2024
 */
@Component
@RequiredArgsConstructor
public class RequestActivateCodeRequestValidatorImpl implements
        RequestActivateCodeRequestValidator {

    private final EmailHelper emailHelper;

    @Override
    public HttpStatus validate(
            RequestActivateCodeResponseBuilder builder,
            String email,
            Boolean emailNotRegistered ) {

        Boolean invalidEmail = !emailHelper.emailValid( email );
        Boolean hasValidationErrors = Boolean.FALSE;
        HttpStatus httpStatus = HttpStatus.OK;

        if ( invalidEmail ) {

            hasValidationErrors = Boolean.TRUE;
        }

        if ( emailNotRegistered ) {

            hasValidationErrors = Boolean.TRUE;
        }

        if ( hasValidationErrors ) {

            httpStatus = HttpStatus.UNPROCESSABLE_ENTITY;
        }

        builder
                .hasValidationErrors( hasValidationErrors )
                .invalidEmail( invalidEmail )
                .emailNotRegistered( emailNotRegistered );

        return httpStatus;
    }
}
