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
package org.greenzone.repository.project;

import java.util.List;
import java.util.Optional;

import org.greenzone.domain.project.Project;
import org.greenzone.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author EN - Dec 27, 2024
 */
public interface ProjectRepository extends JpaRepository<Project, Long> {

    List<Project> findByUserEnabledTrueAndUserActiveTrueAndUserId( Long userId );


    Optional<Project> findByIdAndUser( Long projectId, User user );
}
