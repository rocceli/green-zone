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
package org.greenzone.controller.shared.register.activate.requestcode;

import org.greenzone.service.register.activate.requestcode.ActivateRequestCodeService;
import org.greenzone.service.register.activate.requestcode.RequestActivateCodeRequest;
import org.greenzone.service.register.activate.requestcode.RequestActivateCodeResponse;
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
@RequestMapping( "/api/v1/account/activate/requestcode" )
@RequiredArgsConstructor
public class AccountActivateRequestCodeController {

    private final ActivateRequestCodeService requestCodeService;

    @PostMapping( "" )
    public ResponseEntity<RequestActivateCodeResponse> requestCode(
            @RequestBody RequestActivateCodeRequest request ) {

        return requestCodeService.requestCode( request );
    }
}
