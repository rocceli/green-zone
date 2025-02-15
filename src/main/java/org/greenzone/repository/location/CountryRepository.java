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
package org.greenzone.repository.location;

import java.util.List;

import org.greenzone.domain.location.Country;
import org.greenzone.repository.project.CountProjectsByCountryMapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * @author EN - Dec 20, 2024
 */
public interface CountryRepository extends JpaRepository<Country, Long> {

    @Query( name = "country.getProjectCountsWhereCountGreaterThanZero", nativeQuery = true )
    List<CountProjectsByCountryMapper> getUserProjectsWhereCountGreaterThanZero();
}
