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
package org.greenzone.service.account.edit.lastname;

import org.greenzone.service.account.edit.lastname.EditLastNameResponse.EditLastNameResponseBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

/**
 * @author EN - Dec 17, 2024
 */
@Component
@RequiredArgsConstructor
public class EditLastNameRequestValidatorImpl implements EditLastNameRequestValidator {

    @Value( "${last-name.validation.minimum-length}" )
    private Integer lastNameMinimumLength;

    @Value( "${last-name.validation.maximum-length}" )
    private Integer lastNameMaximumLength;

    @Override
    public HttpStatus validate( EditLastNameResponseBuilder builder, String lastName ) {

        HttpStatus httpStatus = HttpStatus.OK;
        Boolean hasValidationErrors = false;
        Boolean lastNameTooShort = false;
        Boolean lastNameTooLong = false;

        if ( lastName == null || lastName.length() < lastNameMinimumLength ) {

            lastNameTooShort = true;
            hasValidationErrors = true;
        }

        if ( lastName != null && lastName.length() > lastNameMaximumLength ) {

            lastNameTooLong = true;
            hasValidationErrors = true;
        }

        if ( hasValidationErrors ) {

            httpStatus = HttpStatus.UNPROCESSABLE_ENTITY;
        }

        builder
                .hasValidationErrors( hasValidationErrors )
                .lastNameTooLong( lastNameTooLong )
                .lastNameTooShort( lastNameTooShort )
                .lastNameMaximumLength( lastNameMaximumLength )
                .lastNameMinimumLength( lastNameMinimumLength );

        return httpStatus;
    }
}
