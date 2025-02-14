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
package ut.org.greenzone.service.account.activate.requestcode;

import org.greenzone.helper.email.EmailHelper;
import org.greenzone.helper.email.EmailHelperImpl;
import org.greenzone.service.register.activate.requestcode.RequestActivateCodeRequestValidator;
import org.greenzone.service.register.activate.requestcode.RequestActivateCodeRequestValidatorImpl;
import org.greenzone.service.register.activate.requestcode.RequestActivateCodeResponse;
import org.greenzone.service.register.activate.requestcode.RequestActivateCodeResponse.RequestActivateCodeResponseBuilder;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
/**
 * @author EN - Feb 13, 2025
 */
public class RequestActivateCodeRequestValidatorTest {

    private RequestActivateCodeRequestValidator validator;

    @Test
    public void success() {

        EmailHelper emailHelper = new EmailHelperImpl();
        validator = new RequestActivateCodeRequestValidatorImpl( emailHelper );
        String email = "jd@gmail.com";
        Boolean emailNotRegistered = false;
        RequestActivateCodeResponseBuilder builder = RequestActivateCodeResponse.builder();

        HttpStatus httpStatus = validator.validate( builder, email, emailNotRegistered );
        Assert.assertEquals( 200, httpStatus.value() );
        RequestActivateCodeResponse response = builder.build();
        Assert.assertFalse( response.getHasValidationErrors() );
        Assert.assertFalse( response.getInvalidEmail() );
        Assert.assertFalse( response.getEmailNotRegistered() );
    }


    @Test
    public void emailWrongFormat() {

        EmailHelper emailHelper = new EmailHelperImpl();
        validator = new RequestActivateCodeRequestValidatorImpl( emailHelper );
        String email = "jd@@@@gmail.com";
        Boolean emailNotRegistered = false;
        RequestActivateCodeResponseBuilder builder = RequestActivateCodeResponse.builder();

        HttpStatus httpStatus = validator.validate( builder, email, emailNotRegistered );
        Assert.assertEquals( 422, httpStatus.value() );
        RequestActivateCodeResponse response = builder.build();
        Assert.assertTrue( response.getHasValidationErrors() );
        Assert.assertTrue( response.getInvalidEmail() );
        Assert.assertFalse( response.getEmailNotRegistered() );
    }


    @Test
    public void emailNotRegistered() {

        EmailHelper emailHelper = new EmailHelperImpl();
        validator = new RequestActivateCodeRequestValidatorImpl( emailHelper );
        String email = "jd@gmail.com";
        Boolean emailNotRegistered = true;
        RequestActivateCodeResponseBuilder builder = RequestActivateCodeResponse.builder();

        HttpStatus httpStatus = validator.validate( builder, email, emailNotRegistered );
        Assert.assertEquals( 422, httpStatus.value() );
        RequestActivateCodeResponse response = builder.build();
        Assert.assertTrue( response.getHasValidationErrors() );
        Assert.assertFalse( response.getInvalidEmail() );
        Assert.assertTrue( response.getEmailNotRegistered() );
    }
}
