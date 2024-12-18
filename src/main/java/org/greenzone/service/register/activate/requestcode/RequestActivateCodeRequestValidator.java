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
package org.greenzone.service.register.activate.requestcode;

import org.greenzone.service.register.activate.requestcode.RequestActivateCodeResponse.RequestActivateCodeResponseBuilder;
import org.springframework.http.HttpStatus;

/**
 * @author EN - Dec 18, 2024
 */
public interface RequestActivateCodeRequestValidator {

    HttpStatus validate(
            RequestActivateCodeResponseBuilder builder,
            String email,
            Boolean emailNotRegistered );
}
