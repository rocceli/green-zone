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
package org.greenzone.service.project.create;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author EN - Dec 27, 2024
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateProjectResponse {

    private Boolean hasValidationErrors;

    private Boolean projectDescriptionTooShort;
    private Boolean projectDescriptionTooLong;

    private Boolean projectStageTooShort;
    private Boolean projectStageTooLong;

    private Boolean projectSizeAreaTooShort;
    private Boolean projectSizeAreaTooLong;

    private Boolean zipCodeTooLong;
    private Boolean zipCodeTooshort;

    private Boolean line1TooLong;
    private Boolean line1TooShort;

    private Boolean line2TooLong;
    private Boolean line2TooShort;

    private Boolean longitudeTooLong;
    private Boolean longitudeTooShort;

    private Boolean latitudeTooLong;
    private Boolean latitudeTooShort;

    private Boolean countryIdNotSet;

    private Boolean townCityTooShort;
    private Boolean townCityTooLong;
}
