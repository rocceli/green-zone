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
package org.greenzone.controller.home;

import org.greenzone.service.home.HomeInitialData;
import org.greenzone.service.home.HomeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

/**
 * @author EN - Jan 8, 2025
 */
@RestController
@RequestMapping( "/api/v1" )
@RequiredArgsConstructor
public class HomeController {

    private final HomeService homeService;

    @GetMapping( "/" )
    public ResponseEntity<HomeInitialData> getHomeInitialData() {

        return homeService.getHomeInitialData();

    }
}
