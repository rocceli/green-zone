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
package org.greenzone.controller.superadmin.admin;

import org.greenzone.service.admin.AdminService;
import org.greenzone.service.admin.delete.DeleteAdminResponse;
import org.greenzone.service.admin.view.ViewAdminInitialData;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

/**
 * @author EN - Dec 20, 2024
 */
@RestController
@RequestMapping( "/api/v1/superadmin/admin" )
@RequiredArgsConstructor
public class AdminController {

    private final AdminService admService;

    @GetMapping( "/view/initialdata/{adminId}" )
    @PreAuthorize( "hasAuthority('SUPER_ADMIN' )" )
    public ResponseEntity<ViewAdminInitialData> getViewAdminInitialData(
            @PathVariable Long adminId ) {

        ViewAdminInitialData viewAdminInitialData = admService.getViewAdminInitialData( adminId );
        return ResponseEntity.status( HttpStatus.OK ).body( viewAdminInitialData );
    }


    @DeleteMapping( "/delete/{adminId}" )
    @PreAuthorize( "hasAuthority('SUPER_ADMIN' )" )
    public ResponseEntity<DeleteAdminResponse> removeAdmin(
            @PathVariable Long adminId ) {

        ResponseEntity<DeleteAdminResponse> deleteAdminResponse = admService.deleteAdmin( adminId );
        return deleteAdminResponse;
    }
}
