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
package org.greenzone.service.project;

import java.util.ArrayList;
import java.util.List;

import org.greenzone.domain.project.Project;
import org.greenzone.domain.user.User;
import org.greenzone.repository.project.ProjectRepository;
import org.greenzone.repository.user.UserRepository;
import org.greenzone.service.project.view.ProjectTO;
import org.greenzone.service.project.view.ViewProjectsResponse;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

/**
 * @author EN - Dec 28, 2024
 */
@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {

    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;

    private ProjectTO createProject( Project project ) {

        ProjectTO projectTO = ProjectTO.builder()
                .id( project.getId() )
                .address( project.getAddress() )
                .description( project.getDescription() )
                .stage( project.getStage() )
                .sizeArea( project.getSizeArea() )
                .build();

        return projectTO;
    }


    private ProjectTO[] getProjects( List<Project> projects ) {

        List<ProjectTO> projectTOs = new ArrayList<>();

        for ( Project project : projects ) {
            projectTOs.add( createProject( project ) );
        }
        return projectTOs.toArray( new ProjectTO[projects.size()] );

    }


    @Override
    public ViewProjectsResponse getViewProjectsInitialData( User user ) {

        List<Project> projects = projectRepository.findByUserEnabledTrueAndUserActiveTrueAndUserId(
                user.getId() );
        ProjectTO[] projectTOs = getProjects( projects );

        return ViewProjectsResponse.builder().projects( projectTOs ).build();
    }

}
