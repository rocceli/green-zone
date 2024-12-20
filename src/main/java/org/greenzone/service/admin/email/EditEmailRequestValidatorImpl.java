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
package org.greenzone.service.admin.email;

import org.greenzone.helper.email.EmailHelper;
import org.greenzone.service.admin.email.EditEmailResponse.EditEmailResponseBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

/**
 * @author EN - Dec 20, 2024
 */
@Component
@RequiredArgsConstructor
public class EditEmailRequestValidatorImpl implements EditEmailRequestValidator {

    private final EmailHelper emailHelper;

    @Override
    public HttpStatus validate(
            EditEmailResponseBuilder builder, String email, Boolean emailTaken ) {

        HttpStatus httpStatus = HttpStatus.OK;
        Boolean hasValidationErrors = false;
        Boolean emailInvalid = !emailHelper.emailValid( email );

        if ( emailInvalid || emailTaken ) {

            hasValidationErrors = true;
        }

        if ( hasValidationErrors ) {

            httpStatus = HttpStatus.UNPROCESSABLE_ENTITY;
        }

        builder
                .hasValidationErrors( hasValidationErrors )
                .emailTaken( emailTaken )
                .emailInvalid( emailInvalid );

        return httpStatus;
    }
}
