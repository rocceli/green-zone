/*
    ================================================================================================
    This code is part of the Greenzone software.

    GreenZone is a closed source software of EN (Eliah Ngugi) which is owned by EN.
    
        Elijah Ngugi Gachuki
    
    This software is private closed source software.
    
    For further details look at or request greenzone-license.txt for further details.

    Copyright (C) 2025 

    Email:  elijah.ngugi.gachuki@gmail.com
    Domain: N/A

    ================================================================================================
    Author : EN
    ================================================================================================
 */
package org.greenzone.helper.project;

import java.util.ArrayList;
import java.util.List;

import org.greenzone.repository.location.CountryRepository;
import org.greenzone.repository.project.CountProjectsByCountryMapper;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

/**
 * @author EN - Jan 8, 2025
 */
@Component
@RequiredArgsConstructor
public class ProjectHelperImpl implements ProjectHelper {

    private final CountryRepository countryRepository;

    private ProjectCountTO createProjectCountryCountsTO( CountProjectsByCountryMapper mapper ) {

        return ProjectCountTO.builder()
                .countryId( mapper.getCountryId() )
                .countryName( mapper.getCountryName() )
                .projectCount( mapper.getProjectCount() )
                .build();

    }


    @Override
    public ProjectCountTO[] getProjectCountsWhereCountGreaterThanZero() {

        List<CountProjectsByCountryMapper> mapperList = countryRepository
                .getUserProjectsWhereCountGreaterThanZero();

        List<ProjectCountTO> projectsCountsTOList = new ArrayList<>();

        for ( CountProjectsByCountryMapper mapper : mapperList ) {

            projectsCountsTOList.add( createProjectCountryCountsTO( mapper ) );
        }

        return projectsCountsTOList.toArray( new ProjectCountTO[0] );
    }

}
