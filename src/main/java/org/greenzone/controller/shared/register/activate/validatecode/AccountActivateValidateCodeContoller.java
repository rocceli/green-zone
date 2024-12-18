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
package org.greenzone.controller.shared.register.activate.validatecode;

import org.greenzone.service.register.activate.validatecode.ActivateValidateCodeRequest;
import org.greenzone.service.register.activate.validatecode.ActivateValidateCodeResponse;
import org.greenzone.service.register.activate.validatecode.ActivateValidateCodeService;
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
@RequestMapping( "/api/v1/account/activate/validatecode" )
@RequiredArgsConstructor
public class AccountActivateValidateCodeContoller {

    private final ActivateValidateCodeService validateCodeService;

    @PostMapping( "" )
    public ResponseEntity<ActivateValidateCodeResponse> activateAccount(
            @RequestBody ActivateValidateCodeRequest request ) {

        return validateCodeService.validateCode( request );
    }
}
