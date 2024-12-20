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
package org.greenzone.service.admins.create;

import org.greenzone.service.admins.create.CreateAdminResponse.CreateAdminResponseBuilder;
import org.springframework.http.HttpStatus;

/**
 * @author EN - Dec 20, 2024
 */
public interface CreateAdminRequestValidator {

    HttpStatus validate(
            CreateAdminResponseBuilder builder,
            Boolean emailAlreadyRegistered,
            Boolean usernameAlreadyRegistered,
            String firstName,
            String lastName,
            String email,
            String username,
            String password,
            String passwordAlt,
            Long countryId );
}
