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
import java.util.Optional;

import org.greenzone.domain.location.Address;
import org.greenzone.domain.location.Country;
import org.greenzone.domain.project.Project;
import org.greenzone.domain.user.User;
import org.greenzone.helper.country.CountryConverter;
import org.greenzone.helper.country.CountryTO;
import org.greenzone.helper.string.StringHelper;
import org.greenzone.repository.location.AddressRepository;
import org.greenzone.repository.location.CountryRepository;
import org.greenzone.repository.project.ProjectRepository;
import org.greenzone.service.project.create.CreateProjectInitialData;
import org.greenzone.service.project.create.CreateProjectRequest;
import org.greenzone.service.project.create.CreateProjectRequestValidator;
import org.greenzone.service.project.create.CreateProjectResponse;
import org.greenzone.service.project.create.CreateProjectResponse.CreateProjectResponseBuilder;
import org.greenzone.service.project.view.ProjectTO;
import org.greenzone.service.project.view.ViewProjectsResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

/**
 * @author EN - Dec 28, 2024
 */
@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {

    private final CreateProjectRequestValidator createProjectRequestValidator;
    private final AddressRepository addressRepository;
    private final CountryRepository countryRepository;
    private final CountryConverter countryConverter;
    private final StringHelper stringHelper;
    private final ProjectRepository projectRepository;

    private ProjectTO createProject( Project project ) {

        ProjectTO projectTO = ProjectTO.builder()
                .id( project.getId() )
                .createdAt( project.getCreatedAt() )
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


    @Override
    public CreateProjectInitialData getCreateProjectInitialData() {

        CountryTO[] countries = countryConverter.convertEnumToCountryTO();

        CreateProjectInitialData initialData =
                CreateProjectInitialData.builder().countries( countries ).build();

        return initialData;
    }


    @Override
    @Transactional
    public ResponseEntity<CreateProjectResponse> createProject( CreateProjectRequest request,
            User user ) {

        String description = stringHelper.trimAndCapitaliseFirstLetter( request
                .getProjectDescription() );
        String stage = stringHelper.trimAndCapitaliseFirstLetter( request.getProjectStage() );
        String sizeArea = stringHelper.trim( request.getProjectSizeArea() );

        String townCity = stringHelper.trimAndCapitaliseFirstLetter( request
                .getProjectAddressTownCity() );
        String longitude = request.getLongitude();
        String latitude = request.getLatitude();
        String line1 = stringHelper.trimAndLowerCase( request.getProjectAddressLine1() );
        String line2 = stringHelper.trimAndLowerCase( request.getProjectAddressLine2() );
        Long countryId = request.getCountryId();
        String zipCode = stringHelper.trimAndLowerCase( request.getProjectAddressZipPostcode() );

        CreateProjectResponseBuilder builder = CreateProjectResponse.builder();

        HttpStatus httpStatus = createProjectRequestValidator.validate( builder, description,
                sizeArea, stage, line1, line2, longitude, latitude, townCity, countryId );

        CreateProjectResponse response = builder.build();

        if ( !response.getHasValidationErrors() ) {

            boolean addressExists = addressRepository.existsByLongitudeAndLatitude( longitude,
                    latitude );
            if ( addressExists ) {
                httpStatus = HttpStatus.CONFLICT;
            }
            else {

                Optional<Country> countryOpt = countryRepository.findById( countryId );

                if ( countryOpt.isPresent() ) {

                    Country country = countryOpt.get();

                    Address address = Address.builder()
                            .country( country )
                            .latitude( latitude )
                            .longitude( longitude )
                            .zipPostcode( zipCode )
                            .line1( line1 )
                            .line2( line2 )
                            .townCity( townCity )
                            .build();

                    addressRepository.save( address );

                    Project project = Project.builder()
                            .address( address )
                            .description( description )
                            .sizeArea( sizeArea )
                            .user( user )
                            .stage( stage )
                            .build();

                    projectRepository.save( project );
                    httpStatus = HttpStatus.CREATED;
                }
                else {

                    httpStatus = HttpStatus.NOT_ACCEPTABLE;
                }
            }
        }

        response = builder.build();

        return ResponseEntity.status( httpStatus ).body( response );
    }

}
