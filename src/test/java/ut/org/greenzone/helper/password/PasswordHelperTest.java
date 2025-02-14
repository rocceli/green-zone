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
package ut.org.greenzone.helper.password;

import org.greenzone.helper.password.PasswordHelper;
import org.greenzone.helper.password.PasswordHelperImpl;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

/**
 * @author EN - Feb 13, 2025
 */
public class PasswordHelperTest {

    private static PasswordHelper passwordHelper;

    @Test
    public void passwordValid() {

        passwordHelper = new PasswordHelperImpl();
        ReflectionTestUtils.setField( passwordHelper, "passwordMinimumLength", 7 );
        ReflectionTestUtils.setField( passwordHelper, "passwordMaximumLength", 30 );
        ReflectionTestUtils.setField( passwordHelper, "passwordNeedsMixedCase", true );
        ReflectionTestUtils.setField( passwordHelper, "passwordNeedsSpecialCharacters", true );
        ReflectionTestUtils.setField( passwordHelper, "passwordNeedsNumbers", true );
        ReflectionTestUtils.setField( passwordHelper, "passwordNeedsLetters", true );
        String password = "a123AB$#aaaaa";
        boolean valid = passwordHelper.passwordValid( password );
        Assert.assertTrue( valid );
    }


    @Test
    public void passwordTooShort() {

        passwordHelper = new PasswordHelperImpl();
        ReflectionTestUtils.setField( passwordHelper, "passwordMinimumLength", 30 );
        ReflectionTestUtils.setField( passwordHelper, "passwordMaximumLength", 60 );
        ReflectionTestUtils.setField( passwordHelper, "passwordNeedsMixedCase", true );
        ReflectionTestUtils.setField( passwordHelper, "passwordNeedsSpecialCharacters", true );
        ReflectionTestUtils.setField( passwordHelper, "passwordNeedsNumbers", true );
        ReflectionTestUtils.setField( passwordHelper, "passwordNeedsLetters", true );
        String password = "a123AB$#aaaaa";
        boolean valid = passwordHelper.passwordValid( password );
        Assert.assertFalse( valid );
    }


    @Test
    public void passwordTooLong() {

        passwordHelper = new PasswordHelperImpl();
        ReflectionTestUtils.setField( passwordHelper, "passwordMinimumLength", 3 );
        ReflectionTestUtils.setField( passwordHelper, "passwordMaximumLength", 5 );
        ReflectionTestUtils.setField( passwordHelper, "passwordNeedsMixedCase", true );
        ReflectionTestUtils.setField( passwordHelper, "passwordNeedsSpecialCharacters", true );
        ReflectionTestUtils.setField( passwordHelper, "passwordNeedsNumbers", true );
        ReflectionTestUtils.setField( passwordHelper, "passwordNeedsLetters", true );
        String password = "a123AB$#aaaaa";
        boolean valid = passwordHelper.passwordValid( password );
        Assert.assertFalse( valid );
    }


    @Test
    public void passwordHasNoMixedCase() {

        passwordHelper = new PasswordHelperImpl();
        ReflectionTestUtils.setField( passwordHelper, "passwordMinimumLength", 30 );
        ReflectionTestUtils.setField( passwordHelper, "passwordMaximumLength", 60 );
        ReflectionTestUtils.setField( passwordHelper, "passwordNeedsMixedCase", true );
        ReflectionTestUtils.setField( passwordHelper, "passwordNeedsSpecialCharacters", true );
        ReflectionTestUtils.setField( passwordHelper, "passwordNeedsNumbers", true );
        ReflectionTestUtils.setField( passwordHelper, "passwordNeedsLetters", true );
        String password = "a123ab$#aaaaa";
        boolean valid = passwordHelper.passwordValid( password );
        Assert.assertFalse( valid );
    }


    @Test
    public void passwordHasNoSpecialCharacters() {

        passwordHelper = new PasswordHelperImpl();
        ReflectionTestUtils.setField( passwordHelper, "passwordMinimumLength", 30 );
        ReflectionTestUtils.setField( passwordHelper, "passwordMaximumLength", 60 );
        ReflectionTestUtils.setField( passwordHelper, "passwordNeedsMixedCase", true );
        ReflectionTestUtils.setField( passwordHelper, "passwordNeedsSpecialCharacters", true );
        ReflectionTestUtils.setField( passwordHelper, "passwordNeedsNumbers", true );
        ReflectionTestUtils.setField( passwordHelper, "passwordNeedsLetters", true );
        String password = "a123ab$aaaaa";
        boolean valid = passwordHelper.passwordValid( password );
        Assert.assertFalse( valid );
    }


    @Test
    public void passwordHasNoNumbers() {

        passwordHelper = new PasswordHelperImpl();
        ReflectionTestUtils.setField( passwordHelper, "passwordMinimumLength", 30 );
        ReflectionTestUtils.setField( passwordHelper, "passwordMaximumLength", 60 );
        ReflectionTestUtils.setField( passwordHelper, "passwordNeedsMixedCase", true );
        ReflectionTestUtils.setField( passwordHelper, "passwordNeedsSpecialCharacters", true );
        ReflectionTestUtils.setField( passwordHelper, "passwordNeedsNumbers", true );
        ReflectionTestUtils.setField( passwordHelper, "passwordNeedsLetters", true );
        String password = "aBBBab$aaaaa";
        boolean valid = passwordHelper.passwordValid( password );
        Assert.assertFalse( valid );
    }


    @Test
    public void passwordHasNoLetters() {

        passwordHelper = new PasswordHelperImpl();
        ReflectionTestUtils.setField( passwordHelper, "passwordMinimumLength", 30 );
        ReflectionTestUtils.setField( passwordHelper, "passwordMaximumLength", 60 );
        ReflectionTestUtils.setField( passwordHelper, "passwordNeedsMixedCase", true );
        ReflectionTestUtils.setField( passwordHelper, "passwordNeedsSpecialCharacters", true );
        ReflectionTestUtils.setField( passwordHelper, "passwordNeedsNumbers", true );
        ReflectionTestUtils.setField( passwordHelper, "passwordNeedsLetters", true );
        String password = "1234$5678";
        boolean valid = passwordHelper.passwordValid( password );
        Assert.assertFalse( valid );
    }
}
