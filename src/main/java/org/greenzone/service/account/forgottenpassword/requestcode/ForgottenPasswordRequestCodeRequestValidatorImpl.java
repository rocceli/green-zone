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

import org.greenzone.service.account.forgottenpassword.requestcode.ForgottenPasswordRequestCodeResponse.ForgottenPasswordRequestCodeResponseBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

/**
 * @author EN - Dec 18, 2024
 */
@Component
@RequiredArgsConstructor
public class ForgottenPasswordRequestCodeRequestValidatorImpl implements
ForgottenPasswordRequestCodeRequestValidator {

    @Override
    public HttpStatus validate(
            ForgottenPasswordRequestCodeResponseBuilder builder,
            Boolean cannotFindRegisteredEmail ) {

        HttpStatus httpStatus = HttpStatus.OK;
        Boolean hasValidationErrors = false;

        if ( cannotFindRegisteredEmail ) {

            httpStatus = HttpStatus.UNPROCESSABLE_ENTITY;
            hasValidationErrors = true;
        }
        
        builder
                .cannotFindRegisteredEmail( cannotFindRegisteredEmail )
                .hasValidationErrors( hasValidationErrors );

        return httpStatus;
    }
}
