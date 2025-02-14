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
package ut.org.greenzone.service.account.register;

import org.greenzone.helper.password.PasswordHelper;
import org.greenzone.helper.password.PasswordHelperImpl;
import org.greenzone.service.account.edit.password.EditPasswordRequestValidator;
import org.greenzone.service.account.edit.password.EditPasswordRequestValidatorImpl;
import org.greenzone.service.account.edit.password.EditPasswordResponse;
import org.greenzone.service.account.edit.password.EditPasswordResponse.EditPasswordResponseBuilder;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.test.util.ReflectionTestUtils;

/**
 * @author EN - Feb 13, 2025
 */
public class RegisterRequestValidatorTest {

    private EditPasswordRequestValidator passwordValidator;

    /**
     * Note that PasswordHelper which is inside PasswordValidator has its own unit test.
     */
    @Test
    public void passwordValid() {

        PasswordHelper passwordHelper = new PasswordHelperImpl();
        ReflectionTestUtils.setField( passwordHelper, "passwordMinimumLength", 3 );
        ReflectionTestUtils.setField( passwordHelper, "passwordMaximumLength", 21 );
        ReflectionTestUtils.setField( passwordHelper, "passwordNeedsMixedCase", false );
        ReflectionTestUtils.setField( passwordHelper, "passwordNeedsSpecialCharacters", false );
        ReflectionTestUtils.setField( passwordHelper, "passwordNeedsNumbers", false );
        ReflectionTestUtils.setField( passwordHelper, "passwordNeedsLetters", true );

        passwordValidator = new EditPasswordRequestValidatorImpl( passwordHelper );
        ReflectionTestUtils.setField( passwordValidator, "passwordMinimumLength", 3 );
        ReflectionTestUtils.setField( passwordValidator, "passwordMaximumLength", 21 );
        ReflectionTestUtils.setField( passwordValidator, "passwordNeedsMixedCase", false );

        ReflectionTestUtils.setField(
                passwordValidator, "passwordNeedsSpecialCharacters", false );

        ReflectionTestUtils.setField( passwordValidator, "passwordNeedsNumbers", false );
        ReflectionTestUtils.setField( passwordValidator, "passwordNeedsLetters", true );

        EditPasswordResponseBuilder builder = EditPasswordResponse.builder();
        String password = "elation";
        String passwordAlt = "elation";
        HttpStatus httpStatus = passwordValidator.validate( builder, password, passwordAlt );

        Assert.assertEquals( 200, httpStatus.value() );

        EditPasswordResponse response = builder.build();
        Assert.assertFalse( response.getHasValidationErrors() );
        Assert.assertFalse( response.getPasswordInvalid() );
        Assert.assertTrue( response.getPasswordNeedsLetters() );
        Assert.assertFalse( response.getPasswordNeedsMixedCase() );
        Assert.assertFalse( response.getPasswordNeedsNumbers() );
        Assert.assertFalse( response.getPasswordNeedsSpecialCharacters() );
        Assert.assertFalse( response.getPasswordsDoNotMatch() );
        Assert.assertEquals( 3, response.getPasswordMinimumLength().intValue() );
        Assert.assertEquals( 21, response.getPasswordMaximumLength().intValue() );
    }


    @Test
    public void passwordNull() {

        PasswordHelper passwordHelper = new PasswordHelperImpl();
        ReflectionTestUtils.setField( passwordHelper, "passwordMinimumLength", 3 );
        ReflectionTestUtils.setField( passwordHelper, "passwordMaximumLength", 21 );
        ReflectionTestUtils.setField( passwordHelper, "passwordNeedsMixedCase", false );
        ReflectionTestUtils.setField( passwordHelper, "passwordNeedsSpecialCharacters", false );
        ReflectionTestUtils.setField( passwordHelper, "passwordNeedsNumbers", false );
        ReflectionTestUtils.setField( passwordHelper, "passwordNeedsLetters", true );

        passwordValidator = new EditPasswordRequestValidatorImpl( passwordHelper );
        ReflectionTestUtils.setField( passwordValidator, "passwordMinimumLength", 3 );
        ReflectionTestUtils.setField( passwordValidator, "passwordMaximumLength", 21 );
        ReflectionTestUtils.setField( passwordValidator, "passwordNeedsMixedCase", false );

        ReflectionTestUtils.setField(
                passwordValidator, "passwordNeedsSpecialCharacters", false );

        ReflectionTestUtils.setField( passwordValidator, "passwordNeedsNumbers", false );
        ReflectionTestUtils.setField( passwordValidator, "passwordNeedsLetters", true );

        EditPasswordResponseBuilder builder = EditPasswordResponse.builder();
        String password = "elation";
        String passwordAlt = null;
        HttpStatus httpStatus = passwordValidator.validate( builder, password, passwordAlt );

        Assert.assertEquals( 422, httpStatus.value() );

        EditPasswordResponse response = builder.build();
        Assert.assertTrue( response.getHasValidationErrors() );
        Assert.assertTrue( response.getPasswordInvalid() );
        Assert.assertTrue( response.getPasswordNeedsLetters() );
        Assert.assertFalse( response.getPasswordNeedsMixedCase() );
        Assert.assertFalse( response.getPasswordNeedsNumbers() );
        Assert.assertFalse( response.getPasswordNeedsSpecialCharacters() );
        Assert.assertFalse( response.getPasswordsDoNotMatch() );
        Assert.assertEquals( 3, response.getPasswordMinimumLength().intValue() );
        Assert.assertEquals( 21, response.getPasswordMaximumLength().intValue() );
    }


    @Test
    public void passwordDoNotMatch() {

        PasswordHelper passwordHelper = new PasswordHelperImpl();
        ReflectionTestUtils.setField( passwordHelper, "passwordMinimumLength", 3 );
        ReflectionTestUtils.setField( passwordHelper, "passwordMaximumLength", 21 );
        ReflectionTestUtils.setField( passwordHelper, "passwordNeedsMixedCase", false );
        ReflectionTestUtils.setField( passwordHelper, "passwordNeedsSpecialCharacters", false );
        ReflectionTestUtils.setField( passwordHelper, "passwordNeedsNumbers", false );
        ReflectionTestUtils.setField( passwordHelper, "passwordNeedsLetters", true );

        passwordValidator = new EditPasswordRequestValidatorImpl( passwordHelper );
        ReflectionTestUtils.setField( passwordValidator, "passwordMinimumLength", 3 );
        ReflectionTestUtils.setField( passwordValidator, "passwordMaximumLength", 21 );
        ReflectionTestUtils.setField( passwordValidator, "passwordNeedsMixedCase", false );

        ReflectionTestUtils.setField(
                passwordValidator, "passwordNeedsSpecialCharacters", false );

        ReflectionTestUtils.setField( passwordValidator, "passwordNeedsNumbers", false );
        ReflectionTestUtils.setField( passwordValidator, "passwordNeedsLetters", true );

        EditPasswordResponseBuilder builder = EditPasswordResponse.builder();
        String password = "elation";
        String passwordAlt = "blah";
        HttpStatus httpStatus = passwordValidator.validate( builder, password, passwordAlt );

        Assert.assertEquals( 422, httpStatus.value() );

        EditPasswordResponse response = builder.build();
        Assert.assertTrue( response.getHasValidationErrors() );
        Assert.assertFalse( response.getPasswordInvalid() );
        Assert.assertTrue( response.getPasswordNeedsLetters() );
        Assert.assertFalse( response.getPasswordNeedsMixedCase() );
        Assert.assertFalse( response.getPasswordNeedsNumbers() );
        Assert.assertFalse( response.getPasswordNeedsSpecialCharacters() );
        Assert.assertTrue( response.getPasswordsDoNotMatch() );
        Assert.assertEquals( 3, response.getPasswordMinimumLength().intValue() );
        Assert.assertEquals( 21, response.getPasswordMaximumLength().intValue() );
    }
}
