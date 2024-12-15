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
package org.greenzone.controller.logout;

import org.greenzone.service.logout.LogoutResponse;
import org.greenzone.service.logout.LogoutService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

/**
 * @author EN - Dec 16, 2024
 */
@RestController
@RequestMapping( "/api/v1/logout" )
@RequiredArgsConstructor
public class LogoutController {

    private Logger logger = LoggerFactory.getLogger( LogoutController.class );

    @Autowired
    private LogoutService logoutService;

    @PostMapping( "" )
    public ResponseEntity<LogoutResponse> logout(
            HttpServletRequest request, HttpServletResponse response ) {

        logger.info( "request = " + request );
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        logoutService.logout( request, response, authentication );
        LogoutResponse logoutResponse = LogoutResponse.builder().success( Boolean.TRUE ).build();
        return ResponseEntity.ok( logoutResponse );
    }
}
