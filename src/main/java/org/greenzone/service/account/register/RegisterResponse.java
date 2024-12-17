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
package org.greenzone.service.account.register;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author EN - Dec 16, 2024
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterResponse {

    private Boolean hasValidationErrors;

    private Boolean firstNameTooShort;
    private Boolean firstNameTooLong;

    private Boolean lastNameTooShort;
    private Boolean lastNameTooLong;

    private Boolean usernameAlreadyRegistered;
    private Boolean usernameTooShort;
    private Boolean usernameTooLong;

    private Boolean emailInvalid;
    private Boolean emailAlreadyRegistered;

    private Boolean passwordInvalid;
    private Boolean passwordsDoNotMatch;

    private Integer firstNameMinimumLength;
    private Integer firstNameMaximumLength;

    private Integer lastNameMinimumLength;
    private Integer lastNameMaximumLength;

    private Integer usernameMinimumLength;
    private Integer usernameMaximumLength;

    private Integer passwordMinimumLength;
    private Integer passwordMaximumLength;
    private Boolean passwordNeedsMixedCase;
    private Boolean passwordNeedsSpecialCharacters;
    private Boolean passwordNeedsNumbers;
    private Boolean passwordNeedsLetters;
}
