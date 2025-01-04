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
package org.greenzone.service.project.projects.view;

import java.util.Calendar;

import org.greenzone.domain.location.Address;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author EN - Dec 27, 2024
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProjectTO {

    private Long projectId;
    private String projectName;
    private Calendar projectCreatedAt;
    private String projectDescription;
    private String projectStage;
    private String projectSizeArea;
    private Address projectAddress;
}
