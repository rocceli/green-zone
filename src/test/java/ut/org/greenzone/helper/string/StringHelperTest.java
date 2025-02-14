/*
    ================================================================================================
    This code is part of the Greenzone software.

    GreenZone is a closed source software of EN (Eliah Ngugi) which is owned by EN.
    
        Elijah Ngugi Gachuki
    
    This software is private closed source software.
    
    For further details look at or request greenzone-license.txt for further details.

    Copyright (C) 2025 

    Email:  elijah.ngugi.gachuki@gmail.com
    Domain: N/A

    ================================================================================================
    Author : EN
    ================================================================================================
 */
package ut.org.greenzone.helper.string;

import org.greenzone.helper.string.StringHelper;
import org.greenzone.helper.string.StringHelperImpl;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

/**
 * @author EN - Feb 13, 2025
 */
public class StringHelperTest {

    private static StringHelper stringHelper;

    @Test
    public void trim() {

        stringHelper = new StringHelperImpl();
        String testString = " Elijah ";
        String valid = stringHelper.trim( testString );
        Assert.assertEquals( "Elijah", valid );
    }


    @Test
    public void trimAndLowerCase() {

        stringHelper = new StringHelperImpl();
        String testString = " ELIJAH ";
        String valid = stringHelper.trimAndLowerCase( testString );
        Assert.assertEquals( "elijah", valid );
    }


    @Test
    public void trimAndCapitaliseFirstLetter() {

        stringHelper = new StringHelperImpl();
        String testString = " elijah ";
        String valid = stringHelper.trimAndCapitaliseFirstLetter( testString );
        Assert.assertEquals( "Elijah", valid );
    }


    @Test
    public void empty() {

        stringHelper = new StringHelperImpl();
        String testString = "";
        Boolean valid = stringHelper.empty( testString );
        Assert.assertEquals( true, valid );
    }


    @Test
    public void getSubstring() {

        stringHelper = new StringHelperImpl();
        String testString = "Hello World";
        String testString0 = "Hi";
        Assert.assertEquals( "Hello ...", stringHelper.getSubstring( testString, 5 ) );
        Assert.assertEquals( "Hi", stringHelper.getSubstring( testString0, 5 ) );
    }
}
