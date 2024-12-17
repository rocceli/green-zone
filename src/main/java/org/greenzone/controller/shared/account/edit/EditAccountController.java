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
package org.greenzone.controller.shared.account.edit;

import org.greenzone.domain.user.User;
import org.greenzone.helper.loggedin.LoggedInCredentialsHelper;
import org.greenzone.service.account.edit.EditAccountService;
import org.greenzone.service.account.edit.firstname.EditFirstNameRequest;
import org.greenzone.service.account.edit.firstname.EditFirstNameResponse;
import org.greenzone.service.account.edit.initialdata.AccountDetails;
import org.greenzone.service.account.edit.lastname.EditLastNameRequest;
import org.greenzone.service.account.edit.lastname.EditLastNameResponse;
import org.greenzone.service.account.edit.password.EditPasswordRequest;
import org.greenzone.service.account.edit.password.EditPasswordResponse;
import org.greenzone.service.account.edit.username.EditUserNameRequest;
import org.greenzone.service.account.edit.username.EditUserNameResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

/**
 * @author EN - Dec 17, 2024
 */
@RestController
@RequestMapping( "/api/v1/account" )
@RequiredArgsConstructor
public class EditAccountController {

    private final EditAccountService editAccountService;
    private final LoggedInCredentialsHelper loggedInCredentialsHelper;

    /**
     * Shows username, firstName, lastName, email and phone
     * @return ResponseEntity<AccountDetails> -
     * 
     */
    @GetMapping( "/accountdetails" )
    @PreAuthorize( "isAuthenticated()" )
    public ResponseEntity<AccountDetails> getAccountDetails() {

        User user = loggedInCredentialsHelper.getLoggedInUser();
        return editAccountService.getAccountDetails( user );
    }


    /**
     * Just for editing the firstName
     * 
     * @param request
     * @return ResponseEntity<EditFirstNameResponse>
     */
    @PatchMapping( "/firstname" )
    @PreAuthorize( "isAuthenticated()" )
    public ResponseEntity<EditFirstNameResponse> editFirstName(
            @RequestBody EditFirstNameRequest request ) {

        User user = loggedInCredentialsHelper.getLoggedInUser();
        return editAccountService.editFirstName( user, request );
    }


    /**
     * Just for editing the lastName
     * 
     * @param request
     * @return ResponseEntity<EditLastNameResponse>
     */
    @PatchMapping( "/lastname" )
    @PreAuthorize( "isAuthenticated()" )
    public ResponseEntity<EditLastNameResponse> editLastName(
            @RequestBody EditLastNameRequest request ) {

        User user = loggedInCredentialsHelper.getLoggedInUser();
        return editAccountService.editLastName( user, request );
    }

    /**
     * Just for editing the password 
     * 
     * @param request
     * @return
     */
    @PatchMapping( "/password" )
    @PreAuthorize( "isAuthenticated()" )
    public ResponseEntity<EditPasswordResponse> editPassword(
            @RequestBody EditPasswordRequest request ) {

        User user = loggedInCredentialsHelper.getLoggedInUser();
        return editAccountService.editPassword( user, request );
    }


    /**
     * Just for editing the username
     * 
     * @param request
     * @return
     */
    @PatchMapping( "/username" )
    @PreAuthorize( "isAuthenticated()" )
    public ResponseEntity<EditUserNameResponse> editUsername(
            @RequestBody EditUserNameRequest request ) {

        User user = loggedInCredentialsHelper.getLoggedInUser();
        return editAccountService.editUsername( user, request );
    }

}
