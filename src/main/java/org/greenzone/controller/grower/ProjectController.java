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
import org.greenzone.service.project.create.CreateProjectInitialData;
import org.greenzone.service.project.create.CreateProjectRequest;
import org.greenzone.service.project.create.CreateProjectResponse;
import org.greenzone.service.project.edit.EditProjectRequest;
import org.greenzone.service.project.edit.EditProjectResponse;
import org.greenzone.service.project.post.PostService;
import org.greenzone.service.project.post.create.CreatePostRequest;
import org.greenzone.service.project.post.create.CreatePostResponse;
import org.greenzone.service.project.projects.view.ViewProjectsResponse;
import org.greenzone.service.project.view.ViewProjectResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
    private final PostService postService;
    private final LoggedInCredentialsHelper loggedInCredentialsHelper;
    
    @GetMapping( "/view/projects" )
    @PreAuthorize( "hasAuthority('GROWER' )" )
    public ResponseEntity<ViewProjectsResponse> getViewProjectsInitialData() {

        User user = loggedInCredentialsHelper.getLoggedInUser();

        ViewProjectsResponse viewProjectsInitialData = projectservice.getViewProjectsInitialData(
                user );

        return ResponseEntity.status( HttpStatus.OK ).body( viewProjectsInitialData );
    }


    @GetMapping( "/project/{projectId}" )
    public ResponseEntity<ViewProjectResponse> getViewProject(
            @PathVariable Long projectId ) {

        HttpStatus httpStatus = null;

        ViewProjectResponse viewProject = projectservice.getviewProject( projectId );
        if ( viewProject != null ) {
            httpStatus = HttpStatus.OK;
        }
        else {
            httpStatus = HttpStatus.NOT_FOUND;
        }
        return ResponseEntity.status( httpStatus ).body( viewProject );
    }


    @PostMapping( "/project" )
    @PreAuthorize( "hasAuthority('GROWER' )" )
    public ResponseEntity<CreateProjectResponse> createProject(
            @RequestBody CreateProjectRequest request ) {

        User user = loggedInCredentialsHelper.getLoggedInUser();

        return projectservice.createProject( request, user );
    }


    @PatchMapping( "/project/{projectId}" )
    @PreAuthorize( "hasAuthority('GROWER' )" )
    public ResponseEntity<EditProjectResponse> editProject(
            @RequestBody EditProjectRequest request, @PathVariable Long projectId ) {

        User user = loggedInCredentialsHelper.getLoggedInUser();

        EditProjectResponse editProjectResponse = projectservice.editProject( request, projectId,
                user );

        return ResponseEntity.status( HttpStatus.OK ).body( editProjectResponse );
    }


    @GetMapping( "/project/create/initialdata" )
    @PreAuthorize( "hasAuthority('GROWER' )" )
    public ResponseEntity<CreateProjectInitialData> getCreateProjectInitialData() {

        CreateProjectInitialData createProjectInitialData = projectservice
                .getCreateProjectInitialData();

        return ResponseEntity.status( HttpStatus.OK ).body( createProjectInitialData );
    }


    @PostMapping( "{projectId}/post" )
    @PreAuthorize( "hasAuthority('GROWER' )" )
    public ResponseEntity<CreatePostResponse> createPost(
            @RequestBody CreatePostRequest request ) {

        User user = loggedInCredentialsHelper.getLoggedInUser();

        return postService.createPost( request, user );
    }
}
