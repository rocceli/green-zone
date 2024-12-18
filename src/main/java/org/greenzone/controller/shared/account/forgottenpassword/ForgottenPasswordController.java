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
package org.greenzone.controller.shared.account.forgottenpassword;

import org.greenzone.service.account.forgottenpassword.requestcode.ForgottenPasswordRequestCodeRequest;
import org.greenzone.service.account.forgottenpassword.requestcode.ForgottenPasswordRequestCodeResponse;
import org.greenzone.service.account.forgottenpassword.requestcode.ForgottenPasswordRequestCodeService;
import org.greenzone.service.account.forgottenpassword.validatecode.ForgottenPasswordValidateCodeRequest;
import org.greenzone.service.account.forgottenpassword.validatecode.ForgottenPasswordValidateCodeResponse;
import org.greenzone.service.account.forgottenpassword.validatecode.ForgottenPasswordValidateCodeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

/**
 * @author EN - Dec 18, 2024
 */
@CrossOrigin
@RestController
@RequestMapping( "/api/v1/account/forgottenpassword" )
@RequiredArgsConstructor
public class ForgottenPasswordController {

    private final ForgottenPasswordRequestCodeService forgottenPasswordRequestCodeService;
    private final ForgottenPasswordValidateCodeService forgottenPasswordValidateCodeService;

    @PostMapping( "/requestcode" )
    public ResponseEntity<ForgottenPasswordRequestCodeResponse> sendForgottenPasswordEmail(
            @RequestBody ForgottenPasswordRequestCodeRequest request ) {

        return forgottenPasswordRequestCodeService.sendForgottenPasswordEmail( request );
    }


    @PostMapping( "/validate" )
    public ResponseEntity<ForgottenPasswordValidateCodeResponse> verifyForottenPasswordCode(
            @RequestBody ForgottenPasswordValidateCodeRequest request ) {

        return forgottenPasswordValidateCodeService.validate( request );
    }
}
