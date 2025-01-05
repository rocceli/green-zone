/*
    ================================================================================================
    This code is part of the Greenzone software.

    GreenZone is a closed source software of EN (Eliah Ngugi) which is owned by EN.
    
        Elijah Ngugi Gachuki
    
    This software is private closed source software.
    
    For further details look at or request greenzone-license.txt for further details.

    Copyright (C) 2025 

    Email:  elijah.ngugi.gachuki@gmail.com
    Domain: N/A

    ================================================================================================
    Author : EN
    ================================================================================================
 */
package org.greenzone.service.project.create;

import org.greenzone.service.project.create.CreateProjectResponse.CreateProjectResponseBuilder;
import org.springframework.http.HttpStatus;

/**
 * @author EN - Jan 2, 2025
 */
public interface CreateProjectRequestValidator {

    HttpStatus validate(
            CreateProjectResponseBuilder builder,
            String projectName,
            String projectDescription,
            String projectSizeArea,
            String projectStage,
            String projectLine1,
            String projectLine2,
            String longitude,
            String latitude,
            String townCity,
            Long countryId );
}
