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

import org.greenzone.helper.country.CountryTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author EN - Jan 2, 2025
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateProjectInitialData {

    private CountryTO[] countries;
}
