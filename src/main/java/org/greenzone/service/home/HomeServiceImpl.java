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
package org.greenzone.service.home;

import org.greenzone.helper.project.ProjectCountTO;
import org.greenzone.helper.project.ProjectHelper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

/**
 * @author EN - Jan 8, 2025
 */
@Service
@RequiredArgsConstructor
public class HomeServiceImpl implements HomeService {

    private final ProjectHelper projectHelper;

    @Value( "${show-sponsor}" )
    private Boolean showSponsor;

    @Override
    public ResponseEntity<HomeInitialData> getHomeInitialData() {

        ProjectCountTO[] projectCounts = projectHelper.getProjectCountsWhereCountGreaterThanZero();

        HomeInitialData initialData =
                HomeInitialData.builder().projectCountTO( projectCounts ).showSponsor( showSponsor )
                        .build();

        return ResponseEntity.status( HttpStatus.OK ).body( initialData );
    }

}
