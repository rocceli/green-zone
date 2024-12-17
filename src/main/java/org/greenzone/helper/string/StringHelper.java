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
package org.greenzone.helper.string;


/**
 * @author EN - Dec 16, 2024
 */
public interface StringHelper {

    String trim( String string );


    String trimAndLowerCase( String string );


    String trimAndCapitaliseFirstLetter( String string );


    Boolean empty( String string );


    String getSubstring( String string, int maxSize );


    String[] lowerCase( String[] stringArray );
}
