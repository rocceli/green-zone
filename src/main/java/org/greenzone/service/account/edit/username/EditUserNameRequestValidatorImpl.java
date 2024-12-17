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
package org.greenzone.service.account.edit.username;

import org.greenzone.service.account.edit.username.EditUserNameResponse.EditUserNameResponseBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

/**
 * @author EN - Dec 17, 2024
 */
@Component
@RequiredArgsConstructor
public class EditUserNameRequestValidatorImpl implements EditUserNameRequestValidator {

    @Value( "${username.validation.minimum-length}" )
    private Integer usernameMinimumLength;

    @Value( "${username.validation.maximum-length}" )
    private Integer usernameMaximumLength;

    @Override
    public HttpStatus validate( EditUserNameResponseBuilder builder, String username ) {

        HttpStatus httpStatus = HttpStatus.OK;
        Boolean hasValidationErrors = false;
        Boolean usernameTooShort = false;
        Boolean usernameTooLong = false;

        if ( username == null || username.length() < usernameMinimumLength ) {

            usernameTooShort = true;
            hasValidationErrors = true;
        }

        if ( username != null && username.length() > usernameMaximumLength ) {

            usernameTooLong = true;
            hasValidationErrors = true;
        }

        if ( hasValidationErrors ) {

            httpStatus = HttpStatus.UNPROCESSABLE_ENTITY;
        }

        builder
                .hasValidationErrors( hasValidationErrors )
                .usernameTooLong( usernameTooLong )
                .usernameTooShort( usernameTooShort )
                .usernameMaximumLength( usernameMaximumLength )
                .usernameMinimumLength( usernameMinimumLength );

        return httpStatus;
    }
}
