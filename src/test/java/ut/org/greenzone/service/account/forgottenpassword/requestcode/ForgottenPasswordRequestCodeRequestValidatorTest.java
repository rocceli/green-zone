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
package ut.org.greenzone.service.account.forgottenpassword.requestcode;

import org.greenzone.service.account.forgottenpassword.requestcode.ForgottenPasswordRequestCodeRequestValidator;
import org.greenzone.service.account.forgottenpassword.requestcode.ForgottenPasswordRequestCodeRequestValidatorImpl;
import org.greenzone.service.account.forgottenpassword.requestcode.ForgottenPasswordRequestCodeResponse;
import org.greenzone.service.account.forgottenpassword.requestcode.ForgottenPasswordRequestCodeResponse.ForgottenPasswordRequestCodeResponseBuilder;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

/**
 * @author EN - Feb 13, 2025
 */
public class ForgottenPasswordRequestCodeRequestValidatorTest {

    private ForgottenPasswordRequestCodeRequestValidator validator;

    @Test
    public void success() {

        validator = new ForgottenPasswordRequestCodeRequestValidatorImpl();

        ForgottenPasswordRequestCodeResponseBuilder builder =
                ForgottenPasswordRequestCodeResponse.builder();

        Boolean cannotFindRegisteredEmail = false;

        HttpStatus httpStatus = validator.validate( builder, cannotFindRegisteredEmail );

        Assert.assertEquals( 200, httpStatus.value() );
        ForgottenPasswordRequestCodeResponse response = builder.build();
        Assert.assertFalse( response.getHasValidationErrors() );
        Assert.assertFalse( response.getCannotFindRegisteredEmail() );
    }


    @Test
    public void notRegistered() {

        validator = new ForgottenPasswordRequestCodeRequestValidatorImpl();

        ForgottenPasswordRequestCodeResponseBuilder builder =
                ForgottenPasswordRequestCodeResponse.builder();

        Boolean cannotFindRegisteredEmail = true;

        HttpStatus httpStatus = validator.validate( builder, cannotFindRegisteredEmail );

        Assert.assertEquals( 422, httpStatus.value() );
        ForgottenPasswordRequestCodeResponse response = builder.build();
        Assert.assertTrue( response.getHasValidationErrors() );
        Assert.assertTrue( response.getCannotFindRegisteredEmail() );
    }
}
