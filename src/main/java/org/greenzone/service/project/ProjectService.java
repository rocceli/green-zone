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
package org.greenzone.service.project;

import org.greenzone.domain.user.User;
import org.greenzone.service.project.create.CreateProjectInitialData;
import org.greenzone.service.project.create.CreateProjectRequest;
import org.greenzone.service.project.create.CreateProjectResponse;
import org.greenzone.service.project.projects.view.ViewProjectsResponse;
import org.greenzone.service.project.view.ViewProjectResponse;
import org.springframework.http.ResponseEntity;

/**
 * @author EN - Dec 28, 2024
 */
public interface ProjectService {

    ViewProjectsResponse getViewProjectsInitialData( User user );


    CreateProjectInitialData getCreateProjectInitialData();


    ResponseEntity<CreateProjectResponse> createProject( CreateProjectRequest request, User user );


    ViewProjectResponse getviewProject( Long id );
}
