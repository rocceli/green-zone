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
package org.greenzone.service.account.edit.username;

import org.greenzone.service.account.edit.username.EditUserNameResponse.EditUserNameResponseBuilder;
import org.springframework.http.HttpStatus;

/**
 * @author EN - Dec 17, 2024
 */
public interface EditUserNameRequestValidator {

    HttpStatus validate( EditUserNameResponseBuilder builder, String phone );
}
