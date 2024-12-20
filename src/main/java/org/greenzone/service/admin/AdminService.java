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
package org.greenzone.service.admin;

import org.greenzone.domain.user.User;
import org.greenzone.service.admin.delete.DeleteAdminResponse;
import org.greenzone.service.admin.email.EditEmailRequest;
import org.greenzone.service.admin.email.EditEmailResponse;
import org.greenzone.service.admin.view.ViewAdminInitialData;
import org.springframework.http.ResponseEntity;

/**
 * @author EN - Dec 20, 2024
 */
public interface AdminService {

    ViewAdminInitialData getViewAdminInitialData( Long adminId );


    ResponseEntity<EditEmailResponse> editEmail( User user, EditEmailRequest request );


    ResponseEntity<DeleteAdminResponse> deleteAdmin( Long adminId );
}
