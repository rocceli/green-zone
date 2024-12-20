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
package org.greenzone.service.admins.create;

import org.greenzone.domain.location.CountryEnum;
import org.greenzone.helper.email.EmailHelper;
import org.greenzone.helper.password.PasswordHelper;
import org.greenzone.service.admins.create.CreateAdminResponse.CreateAdminResponseBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

/**
 * @author EN - Dec 20, 2024
 */
@Component
@RequiredArgsConstructor
public class CreateAdminRequestValidatorImpl implements CreateAdminRequestValidator {

    private final EmailHelper emailHelper;
    private final PasswordHelper passwordHelper;

    @Value( "${first-name.validation.minimum-length}" )
    private Integer firstNameMinimumLength;

    @Value( "${first-name.validation.maximum-length}" )
    private Integer firstNameMaximumLength;

    @Value( "${last-name.validation.minimum-length}" )
    private Integer lastNameMinimumLength;

    @Value( "${last-name.validation.maximum-length}" )
    private Integer lastNameMaximumLength;

    @Value( "${username.validation.minimum-length}" )
    private Integer usernameMinimumLength;

    @Value( "${username.validation.maximum-length}" )
    private Integer usernameMaximumLength;

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

    @Override
    public HttpStatus validate(
            CreateAdminResponseBuilder builder,
            Boolean emailAlreadyRegistered,
            Boolean usernameAlreadyRegistered,
            String firstName,
            String lastName,
            String email,
            String username,
            String password,
            String passwordAlt,
            Long countryId ) {

        Boolean hasValdationErrors = Boolean.FALSE;
        Boolean emailInvalid = Boolean.FALSE;
        Boolean firstNameTooShort = Boolean.FALSE;
        Boolean firstNameTooLong = Boolean.FALSE;
        Boolean lastNameTooShort = Boolean.FALSE;
        Boolean lastNameTooLong = Boolean.FALSE;
        Boolean usernameTooShort = Boolean.FALSE;
        Boolean usernameTooLong = Boolean.FALSE;
        Boolean passwordInvalid = Boolean.FALSE;
        Boolean passwordsDoNotMatch = Boolean.FALSE;
        Boolean countryIdNotSet = Boolean.FALSE;

        HttpStatus httpStatus = HttpStatus.CREATED;

        if ( usernameAlreadyRegistered ) {

            hasValdationErrors = Boolean.TRUE;
        }

        if ( firstName == null || firstName.length() < firstNameMinimumLength ) {

            firstNameTooShort = Boolean.TRUE;
            hasValdationErrors = Boolean.TRUE;
        }

        if ( firstName != null && firstName.length() > firstNameMaximumLength ) {

            firstNameTooLong = Boolean.TRUE;
            hasValdationErrors = Boolean.TRUE;
        }

        if ( lastName == null || lastName.length() < lastNameMinimumLength ) {

            lastNameTooShort = Boolean.TRUE;
            hasValdationErrors = Boolean.TRUE;
        }

        if ( lastName != null && lastName.length() > lastNameMaximumLength ) {

            lastNameTooLong = Boolean.TRUE;
            hasValdationErrors = Boolean.TRUE;
        }

        if ( username == null || username.length() < usernameMinimumLength ) {

            usernameTooShort = Boolean.TRUE;
            hasValdationErrors = Boolean.TRUE;
        }

        if ( username != null && username.length() > usernameMaximumLength ) {

            usernameTooLong = Boolean.TRUE;
            hasValdationErrors = Boolean.TRUE;
        }

        if ( !emailHelper.emailValid( email ) ) {

            emailInvalid = Boolean.TRUE;
            hasValdationErrors = Boolean.TRUE;
        }

        if ( password != null && !password.equals( passwordAlt ) ) {

            passwordsDoNotMatch = Boolean.TRUE;
            hasValdationErrors = Boolean.TRUE;
        }

        if ( countryId == null || !CountryEnum.exists( countryId ) ) {
            countryIdNotSet = Boolean.TRUE;
            hasValdationErrors = Boolean.TRUE;
        }
        else {

            Boolean passwordValid = passwordHelper.passwordValid( password );
            passwordInvalid = !passwordValid;

            if ( passwordInvalid ) {

                hasValdationErrors = Boolean.TRUE;
            }
        }

        if ( hasValdationErrors ) {

            httpStatus = HttpStatus.UNPROCESSABLE_ENTITY;
        }

        builder
                .firstNameTooShort( firstNameTooShort )
                .firstNameTooLong( firstNameTooLong )
                .lastNameTooShort( lastNameTooShort )
                .lastNameTooLong( lastNameTooLong )
                .countryId( countryIdNotSet )
                .usernameTooShort( usernameTooShort )
                .usernameTooLong( usernameTooLong )
                .usernameAlreadyRegistered( usernameAlreadyRegistered )
                .emailInvalid( emailInvalid )
                .emailAlreadyRegistered( emailAlreadyRegistered )
                .passwordsDoNotMatch( passwordsDoNotMatch )
                .passwordInvalid( passwordInvalid )
                .passwordMinimumLength( passwordMinimumLength )
                .passwordMaximumLength( passwordMaximumLength )
                .passwordNeedsLetters( passwordNeedsLetters )
                .passwordNeedsNumbers( passwordNeedsNumbers )
                .passwordNeedsMixedCase( passwordNeedsMixedCase )
                .passwordNeedsSpecialCharacters( passwordNeedsSpecialCharacters )
                .firstNameMinimumLength( firstNameMinimumLength )
                .firstNameMaximumLength( firstNameMaximumLength )
                .lastNameMinimumLength( lastNameMinimumLength )
                .lastNameMaximumLength( lastNameMaximumLength )
                .usernameMinimumLength( usernameMinimumLength )
                .usernameMaximumLength( usernameMaximumLength )
                .hasValdationErrors( hasValdationErrors );

        return httpStatus;
    }
}
