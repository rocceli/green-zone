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
package ut.org.greenzone.service.account.edit.username;

import org.greenzone.service.account.edit.username.EditUserNameRequestValidator;
import org.greenzone.service.account.edit.username.EditUserNameRequestValidatorImpl;
import org.greenzone.service.account.edit.username.EditUserNameResponse;
import org.greenzone.service.account.edit.username.EditUserNameResponse.EditUserNameResponseBuilder;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.test.util.ReflectionTestUtils;

/**
 * @author EN - Feb 13, 2025
 */
public class EditUsernameRequestValidatorTest {

    private static final String USERNAME = "elation";

    private EditUserNameRequestValidator editUsernameRequestValidator;

    @Test
    public void success() {

        editUsernameRequestValidator = new EditUserNameRequestValidatorImpl();
        ReflectionTestUtils.setField( editUsernameRequestValidator, "usernameMinimumLength", 3 );
        ReflectionTestUtils.setField( editUsernameRequestValidator, "usernameMaximumLength", 32 );

        EditUserNameResponseBuilder builder = EditUserNameResponse.builder();
        HttpStatus httpStatus = editUsernameRequestValidator.validate( builder, USERNAME );

        Assert.assertEquals( 200, httpStatus.value() );

        EditUserNameResponse response = builder.build();
        Assert.assertFalse( response.getHasValidationErrors() );
        Assert.assertFalse( response.getUsernameTooLong() );
        Assert.assertFalse( response.getUsernameTooShort() );
        Assert.assertEquals( 32, response.getUsernameMaximumLength().intValue() );
        Assert.assertEquals( 3, response.getUsernameMinimumLength().intValue() );
    }


    @Test
    public void usernameTooLong() {

        editUsernameRequestValidator = new EditUserNameRequestValidatorImpl();
        ReflectionTestUtils.setField( editUsernameRequestValidator, "usernameMinimumLength", 3 );
        ReflectionTestUtils.setField( editUsernameRequestValidator, "usernameMaximumLength", 4 );

        EditUserNameResponseBuilder builder = EditUserNameResponse.builder();
        HttpStatus httpStatus = editUsernameRequestValidator.validate( builder, USERNAME );

        Assert.assertEquals( 422, httpStatus.value() );

        EditUserNameResponse response = builder.build();
        Assert.assertTrue( response.getHasValidationErrors() );
        Assert.assertTrue( response.getUsernameTooLong() );
        Assert.assertFalse( response.getUsernameTooShort() );
        Assert.assertEquals( 4, response.getUsernameMaximumLength().intValue() );
        Assert.assertEquals( 3, response.getUsernameMinimumLength().intValue() );
    }


    @Test
    public void usernameTooShort() {

        editUsernameRequestValidator = new EditUserNameRequestValidatorImpl();
        ReflectionTestUtils.setField( editUsernameRequestValidator, "usernameMinimumLength", 3 );
        ReflectionTestUtils.setField( editUsernameRequestValidator, "usernameMaximumLength", 4 );

        EditUserNameResponseBuilder builder = EditUserNameResponse.builder();
        HttpStatus httpStatus = editUsernameRequestValidator.validate( builder, "el" );

        Assert.assertEquals( 422, httpStatus.value() );

        EditUserNameResponse response = builder.build();
        Assert.assertTrue( response.getHasValidationErrors() );
        Assert.assertFalse( response.getUsernameTooLong() );
        Assert.assertTrue( response.getUsernameTooShort() );
        Assert.assertEquals( 4, response.getUsernameMaximumLength().intValue() );
        Assert.assertEquals( 3, response.getUsernameMinimumLength().intValue() );
    }
}
