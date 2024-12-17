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
package org.greenzone.service.account.register;

import org.greenzone.service.account.register.RegisterResponse.RegisterResponseBuilder;
import org.springframework.http.HttpStatus;;

/**
 * @author EN - Dec 16, 2024
 */
public interface RegisterRequestValidator {

    HttpStatus validate(
            RegisterResponseBuilder builder,
            Boolean emailAlreadyRegistered,
            Boolean usernameAlreadyRegistered,
            String firstName,
            String lastName,
            String email,
            String username,
            String password,
            String passwordAlt );
}
