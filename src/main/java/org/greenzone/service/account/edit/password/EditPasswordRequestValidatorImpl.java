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
package org.greenzone.service.account.edit.password;

import org.greenzone.helper.password.PasswordHelper;
import org.greenzone.service.account.edit.password.EditPasswordResponse.EditPasswordResponseBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

/**
 * @author EN - Dec 17, 2024
 */
@Component
@RequiredArgsConstructor
public class EditPasswordRequestValidatorImpl implements EditPasswordRequestValidator {

    @Value( "${password.validation.minimum-length}" )
    private Integer passwordMinimumLength;

    @Value( "${password.validation.maximum-length}" )
    private Integer passwordMaximumLength;

    @Value( "${password.validation.needs-mixed-case}" )
    private Boolean passwordNeedsMixedCase;

    @Value( "${password.validation.needs-special-characters}" )
    private Boolean passwordNeedsSpecialCharacters;

    @Value( "${password.validation.needs-numbers}" )
    private Boolean passwordNeedsNumbers;

    @Value( "${password.validation.needs-letters}" )
    private Boolean passwordNeedsLetters;

    private final PasswordHelper passwordHelper;

    @Override
    public HttpStatus validate( EditPasswordResponseBuilder builder, String password,
            String passwordAlt ) {

        HttpStatus httpStatus = HttpStatus.OK;
        Boolean hasValidationErrors = false;
        Boolean passwordInvalid = false;
        Boolean passwordsDoNotMatch = false;

        if ( password == null || passwordAlt == null ) {

            hasValidationErrors = true;
            passwordInvalid = true;

        }
        else if ( !password.equals( passwordAlt ) ) {

            hasValidationErrors = true;
            passwordsDoNotMatch = true;
        }
        else {

            passwordInvalid = !passwordHelper.passwordValid( password );

            if ( passwordInvalid ) {

                hasValidationErrors = true;
            }
        }

        if ( hasValidationErrors ) {

            httpStatus = HttpStatus.UNPROCESSABLE_ENTITY;
        }

        builder
                .hasValidationErrors( hasValidationErrors )
                .passwordInvalid( passwordInvalid )
                .passwordMaximumLength( passwordMaximumLength )
                .passwordMinimumLength( passwordMinimumLength )
                .passwordNeedsLetters( passwordNeedsLetters )
                .passwordNeedsMixedCase( passwordNeedsMixedCase )
                .passwordNeedsNumbers( passwordNeedsNumbers )
                .passwordNeedsSpecialCharacters( passwordNeedsSpecialCharacters )
                .passwordsDoNotMatch( passwordsDoNotMatch );

        return httpStatus;
    }
}
