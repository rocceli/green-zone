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
package org.greenzone.helper.roles;

import java.util.Set;

import org.greenzone.domain.user.Role;
import org.greenzone.domain.user.User;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

/**
 * @author EN - Dec 16, 2024
 */
@Component
@RequiredArgsConstructor
public class RoleHelperImpl implements RoleHelper {

    @Override
    public String[] getRoles( User user ) {

        Set<Role> roles = user.getRoles();
        String[] _roles = new String[roles.size()];

        int i = 0;

        for ( Role role : roles ) {

            _roles[i] = role.getName();
            i++;
        }

        return _roles;
    }
}
