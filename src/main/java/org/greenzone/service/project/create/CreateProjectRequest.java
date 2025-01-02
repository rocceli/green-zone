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
public class CreateProjectRequest {

    private String projectDescription;
    private String projectSizeArea;
    private String projectStage;
    private String projectAddressLine1;
    private String projectAddressLine2;
    private String projectAddressTownCity;
    private String projectAddressZipPostcode;
    private String latitude;
    private Long countryId;
    private String longitude;
}
