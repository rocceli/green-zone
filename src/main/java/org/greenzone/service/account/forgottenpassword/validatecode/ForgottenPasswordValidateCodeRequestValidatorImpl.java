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

import org.greenzone.helper.password.PasswordHelper;
import org.greenzone.service.account.forgottenpassword.validatecode.ForgottenPasswordValidateCodeResponse.ForgottenPasswordValidateCodeResponseBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

/**
 * @author EN - Dec 18, 2024
 */
@Component
@RequiredArgsConstructor
public class ForgottenPasswordValidateCodeRequestValidatorImpl implements
        ForgottenPasswordValidateCodeRequestValidator {

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
    public HttpStatus validate(
            ForgottenPasswordValidateCodeResponseBuilder builder,
            Boolean userNotFound,
            String password,
            String passwordAlt ) {

        Boolean hasValidationErrors = false;
        Boolean passwordsDoNotMatch = false;
        Boolean passwordInvalid = false;
        HttpStatus httpStatus = HttpStatus.OK;

        if ( !password.equals( passwordAlt ) ) {

            hasValidationErrors = true;
            passwordsDoNotMatch = true;
        }
        else {
            Boolean passwordValid = passwordHelper.passwordValid( password );
            passwordInvalid = !passwordValid;

            if ( passwordInvalid ) {

                hasValidationErrors = true;
            }
        }

        if ( userNotFound ) {

            hasValidationErrors = true;
        }

        if ( hasValidationErrors ) {

            httpStatus = HttpStatus.UNPROCESSABLE_ENTITY;
        }

        builder
                .hasValidationErrors( hasValidationErrors )
                .passwordInvalid( passwordsDoNotMatch )
                .passwordMaximumLength( passwordMaximumLength )
                .passwordMinimumLength( passwordMinimumLength )
                .passwordNeedsLetters( passwordNeedsLetters )
                .passwordNeedsMixedCase( passwordNeedsMixedCase )
                .passwordNeedsNumbers( passwordNeedsNumbers )
                .passwordNeedsSpecialCharacters( passwordNeedsSpecialCharacters )
                .passwordsDoNotMatch( passwordsDoNotMatch )
                .userNotFound( userNotFound );

        return httpStatus;
    }
}
