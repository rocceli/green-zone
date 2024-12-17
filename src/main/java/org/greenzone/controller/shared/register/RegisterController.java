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
package org.greenzone.controller.shared.register;

import org.greenzone.service.account.register.RegisterRequest;
import org.greenzone.service.account.register.RegisterResponse;
import org.greenzone.service.account.register.RegisterService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

/**
 * @author EN - Dec 16, 2024
 */
@RestController
@RequestMapping( "/api/v1/register" )
@RequiredArgsConstructor
public class RegisterController {

    private final RegisterService registerService;

    @PostMapping( "/save" )
    public ResponseEntity<RegisterResponse> registerIntern(
            @RequestBody RegisterRequest request ) {

        return registerService.register( request );
    }
}
