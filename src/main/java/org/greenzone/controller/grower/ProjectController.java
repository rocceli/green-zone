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
package org.greenzone.controller.grower;

import org.greenzone.domain.user.User;
import org.greenzone.helper.loggedin.LoggedInCredentialsHelper;
import org.greenzone.service.project.ProjectService;
import org.greenzone.service.project.view.ViewProjectsResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

/**
 * @author EN - Dec 28, 2024
 */
@RestController
@RequestMapping( "/api/v1/grower" )
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectservice;
    private final LoggedInCredentialsHelper loggedInCredentialsHelper;
    
    @GetMapping( "/view/projects" )
    @PreAuthorize( "hasAuthority('GROWER' )" )
    public ResponseEntity<ViewProjectsResponse> getViewProjectsInitialData() {

        User user = loggedInCredentialsHelper.getLoggedInUser();

        ViewProjectsResponse viewProjectsInitialData = projectservice.getViewProjectsInitialData(
                user );

        return ResponseEntity.status( HttpStatus.OK ).body( viewProjectsInitialData );
    }
}
