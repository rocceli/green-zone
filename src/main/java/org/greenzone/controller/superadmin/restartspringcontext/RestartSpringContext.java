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
package org.greenzone.controller.superadmin.restartspringcontext;

import org.greenzone.domain.user.User;
import org.greenzone.helper.loggedin.LoggedInCredentialsHelper;
import org.greenzone.service.springcontext.RestartSpringContextRequest;
import org.greenzone.service.springcontext.TestResetSpringContextService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

/**
 * @author EN - Dec 21, 2024
 */
@RestController
@RequestMapping( "/api/v1/superadmin/springcontext" )
@RequiredArgsConstructor
public class RestartSpringContext {

    private final TestResetSpringContextService testResetSpringContextService;
    private final LoggedInCredentialsHelper loggedInCredentialsHelper;

    @PatchMapping( "/restart" )
    @PreAuthorize( "hasAuthority('SUPER_ADMIN')" )
    public ResponseEntity<Void> restartSpring(
            @RequestBody RestartSpringContextRequest request ) {

        User user = loggedInCredentialsHelper.getLoggedInUser();
        return testResetSpringContextService.reset( user, request );
    }
}
