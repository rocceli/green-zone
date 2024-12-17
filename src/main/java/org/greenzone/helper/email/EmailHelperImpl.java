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
package org.greenzone.helper.email;

import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

/**
 * @author EN - Dec 16, 2024
 */
@Component
public class EmailHelperImpl implements EmailHelper {

    private String regexEmailPattern = "^(?=.{1,64}@)[A-Za-z0-9_\\-+]+(\\.[A-Za-z0-9_\\-+]+)*@"
            + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";

    private Pattern emailPattern = Pattern.compile( regexEmailPattern );

    @Override
    public Boolean emailValid( String email ) {

        if ( email == null ) {

            return Boolean.FALSE;
        }

        return emailPattern.matcher( email ).matches();
    }
}
