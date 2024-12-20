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

import org.greenzone.domain.location.Continent;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author EN - Dec 20, 2024
 */
public interface ContinentRepository extends JpaRepository<Continent, Long> {

}
