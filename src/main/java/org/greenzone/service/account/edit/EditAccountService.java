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
package org.greenzone.service.account.edit;

import org.greenzone.domain.user.User;
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

/**
 * @author EN - Dec 17, 2024
 */
public interface EditAccountService {


    ResponseEntity<AccountDetails> getAccountDetails( User user );


    ResponseEntity<EditFirstNameResponse> editFirstName( User user, EditFirstNameRequest request );


    ResponseEntity<EditLastNameResponse> editLastName( User user, EditLastNameRequest request );


    ResponseEntity<EditPasswordResponse> editPassword( User user, EditPasswordRequest request );


    ResponseEntity<EditUserNameResponse> editUsername( User user, EditUserNameRequest request );
}
