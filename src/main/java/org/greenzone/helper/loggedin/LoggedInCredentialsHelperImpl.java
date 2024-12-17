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
package org.greenzone.helper.loggedin;

import org.greenzone.domain.user.User;
import org.greenzone.repository.user.UserRepository;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

/**
 * @author EN - Dec 17, 2024
 */
@Component
@RequiredArgsConstructor
public class LoggedInCredentialsHelperImpl implements LoggedInCredentialsHelper {

    private final UserRepository userRepository;

    @Override
    public User getLoggedInUser() {

        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();

        if ( authentication == null || authentication instanceof AnonymousAuthenticationToken ) {

            return null;
        }

        String email = authentication.getName();
        User user = userRepository.findByEmail( email ).get();
        return user;
    }


    @Override
    public String getLoggedInUserEmail() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if ( authentication == null
                || authentication instanceof UsernamePasswordAuthenticationToken ) {

            return null;
        }
        else {

            String email = authentication.getName();
            return email;
        }
    }
}
