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
package org.greenzone.service.springcontext;

import org.greenzone.GreenZoneApplication;
import org.greenzone.domain.user.User;
import org.greenzone.service.shared.exception.MaliciousException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

/**
 * @author EN - Dec 15, 2024
 */
@Service
@RequiredArgsConstructor
public class TestResetSpringContextServiceImpl implements TestResetSpringContextService {

    @Value( "${superadmin.allow-restart}" )
    private Boolean superadminAllowRestart;

    //    private final HashHelper hashHelper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public ResponseEntity<Void> reset( User loggedInUser, RestartSpringContextRequest request ) {

        //        if ( "prod".equals( profile ) ) {

        //
        //            throw new MaliciousException(
        //                    "Malicious reset of spring context and data in environment / spring profile: "
        //                            + profile );
        //        }

        if ( !superadminAllowRestart ) {

            throw new MaliciousException( "Malicious reset of spring context and data." );
        }

        String passwordHash = loggedInUser.getPasswordHash();
        String password = request.getPassword();
        // String requestPasswordHash = hashHelper.getPasswordHashWithBcrypt( password );

        if ( !passwordEncoder.matches( password, passwordHash ) ) {

            throw new MaliciousException( "Malicious reset of spring context and data" );
        }

        GreenZoneApplication.restart();
        return ResponseEntity.noContent().build();
    }
}
