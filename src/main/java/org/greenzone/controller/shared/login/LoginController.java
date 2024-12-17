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
package org.greenzone.controller.shared.login;

import org.greenzone.service.login.email.LoginRequest;
import org.greenzone.service.login.email.LoginResponse;
import org.greenzone.service.login.email.LoginUsingEmailService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

/**
 * @author EN - Dec 17, 2024
 */
@RestController
@RequestMapping( "/api/v1/login" )
@RequiredArgsConstructor
public class LoginController {

    private final LoginUsingEmailService loginUsingEmailService;

    /**
     * Logging in.
     * 
     * @param request - contains email and password
     * @return ResponseEntity<LoginResponse> -
     * If successful (status code 200 OK), the response contains the JWT token.
     * In the case of the user not being active, an email is sent with a link in it to confirm 
     * the user owns that email address.  In this case (status code 403 Forbidden), the response
     * contains needsValidateEmailActivationCode=true.
     * In the case of the user being active but the email or password being incorrect 
     * (status code 401 UNAUTHORIED) the response is null.
     * 
     */
    @PostMapping( "" )
    public ResponseEntity<LoginResponse> authenticate(
            @RequestBody LoginRequest request ) {

        return loginUsingEmailService.authenticate( request );
    }
}
