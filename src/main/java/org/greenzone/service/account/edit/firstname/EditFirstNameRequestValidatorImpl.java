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
package org.greenzone.service.account.edit.firstname;

import org.greenzone.service.account.edit.firstname.EditFirstNameResponse.EditFirstNameResponseBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

/**
 * @author EN - Dec 17, 2024
 */
@Component
@RequiredArgsConstructor
public class EditFirstNameRequestValidatorImpl implements EditFirstNameRequestValidator {

    @Value( "${first-name.validation.minimum-length}" )
    private Integer firstNameMinimumLength;

    @Value( "${first-name.validation.maximum-length}" )
    private Integer firstNameMaximumLength;

    @Override
    public HttpStatus validate( EditFirstNameResponseBuilder builder, String firstName ) {

        HttpStatus httpStatus = HttpStatus.OK;
        Boolean hasValidationErrors = false;
        Boolean firstNameTooShort = false;
        Boolean firstNameTooLong = false;

        if ( firstName == null || firstName.length() < firstNameMinimumLength ) {

            firstNameTooShort = true;
            hasValidationErrors = true;
        }

        if ( firstName != null && firstName.length() > firstNameMaximumLength ) {

            firstNameTooLong = true;
            hasValidationErrors = true;
        }

        if ( hasValidationErrors ) {

            httpStatus = HttpStatus.UNPROCESSABLE_ENTITY;
        }

        builder
                .hasValidationErrors( hasValidationErrors )
                .firstNameTooLong( firstNameTooLong )
                .firstNameTooShort( firstNameTooShort )
                .firstNameMaximumLength( firstNameMaximumLength )
                .firstNameMinimumLength( firstNameMinimumLength );

        return httpStatus;
    }
}
