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
package org.greenzone.service.project.create;

import org.greenzone.domain.location.CountryEnum;
import org.greenzone.service.project.create.CreateProjectResponse.CreateProjectResponseBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

/**
 * @author EN - Jan 2, 2025
 */
@Component
@RequiredArgsConstructor
public class CreateProjectRequestValidatorImpl implements CreateProjectRequestValidator {

    @Value( "${username.validation.minimum-length}" )
    private Integer projectNameMinimumLength;

    @Value( "${username.validation.maximum-length}" )
    private Integer projectNameMaximumLength;

    @Value( "${project-description.validation.minimum-length}" )
    private Integer projectDescriptionMinimumLength;

    @Value( "${project-description.validation.maximum-length}" )
    private Integer projectDescriptionMaximumLength;

    @Value( "${project-stage.validation.minimum-length}" )
    private Integer projectStageMinimumLength;

    @Value( "${project-stage.validation.maximum-length}" )
    private Integer projectStageMaximumLength;

    @Value( "${project-size-area.validation.minimum-length}" )
    private Integer projectSizeAreaMinimumLength;

    @Value( "${project-size-area.validation.maximum-length}" )
    private Integer projectSizeAreaMaximumLength;

    @Value( "${address-line1.validation.maximum-length}" )
    private Integer addressLine1MaximumLength;

    @Value( "${address-line1.validation.minimum-length}" )
    private Integer addressLine1MinimumLength;

    @Value( "${address-line2.validation.maximum-length}" )
    private Integer addressLine2MaximumLength;

    @Value( "${address-line2.validation.minimum-length}" )
    private Integer addressLine2MinimumLength;

    @Value( "${address-longitude.validation.maximum-length}" )
    private Integer longitudeMaximumLength;

    @Value( "${address-longitude.validation.minimum-length}" )
    private Integer longitudeMinimumLength;

    @Value( "${address-town-city.validation.maximum-length}" )
    private Integer addressTownCityMaximumLength;

    @Value( "${address-town-city.validation.minimum-length}" )
    private Integer addressTownCityMinimumLength;

    @Value( "${address-latitude.validation.maximum-length}" )
    private Integer latitudeMaximumLength;

    @Value( "${address-latitude.validation.minimum-length}" )
    private Integer latitudeMinimumLength;

    @Override
    public HttpStatus validate( CreateProjectResponseBuilder builder, String projectName,
            String projectDescription,
            String projectSizeArea, String projectStage, String projectLine1, String projectLine2,
            String longitude, String latitude, String townCity, Long countryId ) {

        Boolean hasValidationErrors = Boolean.FALSE;

        Boolean projectNameTooShort = Boolean.FALSE;
        Boolean projectNameTooLong = Boolean.FALSE;

        Boolean projectDescriptionTooShort = Boolean.FALSE;
        Boolean projectDescriptionTooLong = Boolean.FALSE;

        Boolean projectStageTooShort = Boolean.FALSE;
        Boolean projectStageTooLong = Boolean.FALSE;

        Boolean projectSizeAreaTooShort = Boolean.FALSE;
        Boolean projectSizeAreaTooLong = Boolean.FALSE;

        Boolean zipCodeTooLong = Boolean.FALSE;
        Boolean zipCodeTooshort = Boolean.FALSE;

        Boolean line1TooLong = Boolean.FALSE;
        Boolean line1TooShort = Boolean.FALSE;

        Boolean line2TooLong = Boolean.FALSE;
        Boolean line2TooShort = Boolean.FALSE;

        Boolean longitudeTooLong = Boolean.FALSE;
        Boolean longitudeTooShort = Boolean.FALSE;

        Boolean latitudeTooLong = Boolean.FALSE;
        Boolean latitudeTooShort = Boolean.FALSE;

        Boolean countryIdNotSet = Boolean.FALSE;

        Boolean townCityTooShort = Boolean.FALSE;
        Boolean townCityTooLong = Boolean.FALSE;

        HttpStatus httpStatus = HttpStatus.CREATED;

        if ( projectName == null || projectName
                .length() < projectDescriptionMinimumLength ) {

            projectNameTooShort = Boolean.TRUE;
            hasValidationErrors = Boolean.TRUE;
        }

        if ( projectName != null && projectName
                .length() > projectDescriptionMaximumLength ) {

            projectNameTooLong = Boolean.TRUE;
            hasValidationErrors = Boolean.TRUE;
        }

        if ( projectName == null || projectName
                .length() < projectNameMinimumLength ) {

            projectNameTooShort = Boolean.TRUE;
            hasValidationErrors = Boolean.TRUE;
        }

        if ( projectName != null && projectName
                .length() > projectNameMaximumLength ) {

            projectNameTooLong = Boolean.TRUE;
            hasValidationErrors = Boolean.TRUE;
        }

        if ( projectDescription == null || projectDescription
                .length() < projectDescriptionMinimumLength ) {

            projectDescriptionTooShort = Boolean.TRUE;
            hasValidationErrors = Boolean.TRUE;
        }

        if ( projectDescription != null && projectDescription
                .length() > projectDescriptionMaximumLength ) {

            projectDescriptionTooLong = Boolean.TRUE;
            hasValidationErrors = Boolean.TRUE;
        }

        if ( projectStage == null || projectStage
                .length() < projectStageMinimumLength ) {

            projectStageTooShort = Boolean.TRUE;
            hasValidationErrors = Boolean.TRUE;
        }

        if ( projectStage != null && projectStage
                .length() > projectStageMaximumLength ) {

            projectStageTooLong = Boolean.TRUE;
            hasValidationErrors = Boolean.TRUE;
        }

        if ( projectSizeArea != null && projectSizeArea
                .length() > projectSizeAreaMaximumLength ) {

            projectSizeAreaTooLong = Boolean.TRUE;
            hasValidationErrors = Boolean.TRUE;
        }

        if ( projectSizeArea == null || projectSizeArea
                .length() < projectSizeAreaMinimumLength ) {

            projectSizeAreaTooShort = Boolean.TRUE;
            hasValidationErrors = Boolean.TRUE;
        }

        if ( projectLine1 != null ) {
            if ( projectLine1.length() < addressLine1MinimumLength ) {
                line1TooShort = true;
                hasValidationErrors = true;
            }
            if ( projectLine1.length() > addressLine1MaximumLength ) {
                line1TooLong = true;
                hasValidationErrors = true;
            }
        }

        if ( projectLine2 != null ) {
            if ( projectLine2.length() < addressLine2MinimumLength ) {
                line2TooShort = true;
                hasValidationErrors = true;
            }
            if ( projectLine2.length() > addressLine2MaximumLength ) {
                line2TooLong = true;
                hasValidationErrors = true;
            }
        }

        if ( townCity != null && townCity
                .length() > addressTownCityMaximumLength ) {

            townCityTooLong = Boolean.TRUE;
            hasValidationErrors = Boolean.TRUE;
        }

        if ( townCity == null || townCity
                .length() < addressTownCityMinimumLength ) {

            townCityTooShort = Boolean.TRUE;
            hasValidationErrors = Boolean.TRUE;
        }

        if ( longitude != null && longitude
                .length() > longitudeMaximumLength ) {

            longitudeTooLong = Boolean.TRUE;
            hasValidationErrors = Boolean.TRUE;
        }

        if ( longitude == null || longitude
                .length() < longitudeMinimumLength ) {

            longitudeTooShort = Boolean.TRUE;
            hasValidationErrors = Boolean.TRUE;
        }

        if ( latitude != null && latitude
                .length() > latitudeMaximumLength ) {

            latitudeTooLong = Boolean.TRUE;
            hasValidationErrors = Boolean.TRUE;
        }

        if ( latitude == null || latitude
                .length() < latitudeMinimumLength ) {

            latitudeTooShort = Boolean.TRUE;
            hasValidationErrors = Boolean.TRUE;
        }

        if ( countryId == null || !CountryEnum.exists( countryId ) ) {
            countryIdNotSet = Boolean.TRUE;
            hasValidationErrors = Boolean.TRUE;
        }

        if ( hasValidationErrors ) {

            httpStatus = HttpStatus.UNPROCESSABLE_ENTITY;
        }

        builder
                .hasValidationErrors( hasValidationErrors )
                .countryIdNotSet( countryIdNotSet )
                .projectNameTooLong( projectNameTooLong )
                .projectNameTooShort( projectNameTooShort )
                .latitudeTooLong( latitudeTooLong )
                .latitudeTooShort( latitudeTooShort )
                .line1TooLong( line1TooLong )
                .line1TooShort( line1TooShort )
                .line2TooLong( line2TooLong )
                .line2TooShort( line2TooShort )
                .longitudeTooLong( longitudeTooLong )
                .longitudeTooShort( longitudeTooShort )
                .projectDescriptionTooLong( projectDescriptionTooLong )
                .projectDescriptionTooShort( projectDescriptionTooShort )
                .projectSizeAreaTooLong( projectSizeAreaTooLong )
                .projectSizeAreaTooShort( projectSizeAreaTooShort )
                .projectStageTooLong( projectStageTooLong )
                .projectStageTooShort( projectStageTooShort )
                .townCityTooLong( townCityTooLong )
                .townCityTooShort( townCityTooShort )
                .zipCodeTooLong( zipCodeTooLong )
                .zipCodeTooshort( zipCodeTooshort );

        return httpStatus;
    }

}
