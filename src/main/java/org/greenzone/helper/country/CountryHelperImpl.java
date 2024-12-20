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

import java.util.ArrayList;
import java.util.List;

import org.greenzone.domain.location.Country;
import org.greenzone.repository.location.CountryRepository;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

/**
 * @author EN - Dec 20, 2024
 */
@Component
@RequiredArgsConstructor
public class CountryHelperImpl implements CountryHelper {

    private final CountryRepository countryRepository;
    private final CountryTOComparator countryTOComparator;

    private CountryTO createCountryTO( Country country ) {

        CountryTO countryTO =
                CountryTO.builder()
                        .countryId( country.getId() )
                        .name( country.getName() )
                        .build();

        return countryTO;
    }


    @Override
    public CountryTO[] getCountries() {

        List<Country> countryList = countryRepository.findAll();
        List<CountryTO> countryTOList = new ArrayList<>();

        for ( Country country : countryList ) {

            countryTOList.add( createCountryTO( country ) );
        }

        countryTOList.sort( countryTOComparator );
        CountryTO[] countries = countryTOList.toArray( new CountryTO[countryTOList.size()] );
        return countries;
    }
}
