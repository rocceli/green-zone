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
package org.greenzone.helper.country;

import java.util.Arrays;

import org.greenzone.domain.location.CountryEnum;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

/**
 * @author EN - Dec 20, 2024
 */
@Component
@RequiredArgsConstructor
public class CountryConverterImpl implements CountryConverter {

    public CountryTO[] convertEnumToCountryTO() {

        return Arrays.stream( CountryEnum.values() )
                .map( country -> new CountryTO( country.getId(), country.getName() ) )
                .toArray( CountryTO[]::new );
    }
}
