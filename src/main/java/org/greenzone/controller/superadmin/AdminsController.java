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
package org.greenzone.controller.superadmin;

import org.greenzone.service.admins.AdminsService;
import org.greenzone.service.admins.create.CreateAdminInitialData;
import org.greenzone.service.admins.create.CreateAdminRequest;
import org.greenzone.service.admins.create.CreateAdminResponse;
import org.greenzone.service.admins.view.ViewAdminsInitialData;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

/**
 * @author EN - Dec 20, 2024
 */
@RestController
@RequestMapping( "/api/v1/superadmin/admins" )
@RequiredArgsConstructor
public class AdminsController {

    private final AdminsService adminService;

    @GetMapping( "/view/initialdata" )
    @PreAuthorize( "hasAuthority('SUPER_ADMIN' )" )
    public ResponseEntity<ViewAdminsInitialData> getViewAdminsInitialData() {

        ViewAdminsInitialData viewAdminsInitialData = adminService.getViewAdminsInitialData();
        return ResponseEntity.status( HttpStatus.OK ).body( viewAdminsInitialData );
    }


    @GetMapping( "/create/initialdata" )
    @PreAuthorize( "hasAuthority('SUPER_ADMIN' )" )
    public ResponseEntity<CreateAdminInitialData> getCreateAdminInitialData() {

        return adminService.getCreateAdminInitialData();
    }


    @PostMapping( "/create" )
    @PreAuthorize( "hasAuthority('SUPER_ADMIN' )" )
    public ResponseEntity<CreateAdminResponse> createAdmin(
            @RequestBody CreateAdminRequest request ) {

        return adminService.createAdmin( request );
    }

}
