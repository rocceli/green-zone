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

import java.util.Arrays;

import org.springframework.stereotype.Component;

/**
 * @author EN - Dec 16, 2024
 */
@Component
public class StringHelperImpl implements StringHelper {

    @Override
    public String trim( String string ) {

        String toReturn;

        if ( string == null ) {

            toReturn = null;
        }
        else {
            toReturn = string.trim();

            if ( toReturn.length() == 0 ) {

                toReturn = null;
            }
        }

        return toReturn;
    }


    @Override
    public String trimAndLowerCase( String string ) {

        String trimmed = trim( string );

        if ( trimmed == null ) {

            return null;
        }

        return trimmed.toLowerCase();
    }


    @Override
    public String trimAndCapitaliseFirstLetter( String string ) {

        String trimmedAndLowerCase = trimAndLowerCase( string );

        if ( trimmedAndLowerCase == null || trimmedAndLowerCase.length() == 0 ) {

            return null;
        }

        String firstLetter = trimmedAndLowerCase.substring( 0, 1 ).toUpperCase();
        String restOfLetters = "";

        if ( trimmedAndLowerCase.length() > 1 ) {

            restOfLetters = trimmedAndLowerCase.substring( 1 );
        }

        return firstLetter + restOfLetters;
    }


    @Override
    public Boolean empty( String string ) {

        if ( trim( string ) == null ) {

            return true;
        }

        return false;
    }


    @Override
    public String getSubstring( String string, int maxSize ) {

        if ( string == null ) {

            return null;
        }

        int size = string.length();
        String dots = "";

        if ( size > maxSize ) {

            size = maxSize;
            dots = " ...";
        }

        return string.substring( 0, size ) + dots;
    }


    @Override
    public String[] lowerCase( String[] stringArray ) {

        if ( stringArray == null || stringArray.length == 0 ) {

            String[] response = {};
            return response;
        }

        return Arrays.stream( stringArray )
                .map( String::toLowerCase )
                .toArray( String[]::new );
    }
}
