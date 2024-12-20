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
package org.greenzone.repository.admin;

import org.greenzone.domain.admin.Admin;
import org.greenzone.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author EN - Dec 20, 2024
 */
public interface AdminRepository extends JpaRepository<Admin, Long> {

    Admin findByUser( User user );
}
