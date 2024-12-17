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

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author EN - Dec 17, 2024
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EditPasswordResponse {

    private Boolean hasValidationErrors;
    private Boolean passwordInvalid;
    private Boolean passwordsDoNotMatch;
    private Integer passwordMinimumLength;
    private Integer passwordMaximumLength;
    private Boolean passwordNeedsMixedCase;
    private Boolean passwordNeedsSpecialCharacters;
    private Boolean passwordNeedsNumbers;
    private Boolean passwordNeedsLetters;
}
