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
package org.greenzone.service.project.edit;

import org.greenzone.service.project.edit.EditProjectResponse.EditProjectResponseBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

/**
 * @author EN - Jan 5, 2025
 */
@Component
@RequiredArgsConstructor
public class EditProjectRequestValidatorImpl implements EditProjectRequestValidator {

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

    @Override
    public HttpStatus validate( EditProjectResponseBuilder builder, String projectName,
            String projectDescription, String projectSizeArea, String projectStage ) {

        boolean allValuesNull = projectName == null
                && projectDescription == null
                && projectSizeArea == null
                && projectStage == null;

        if ( allValuesNull ) {
            return HttpStatus.UNPROCESSABLE_ENTITY;
        }

        Boolean hasValidationErrors = Boolean.FALSE;

        if ( projectName != null ) {
            if ( projectName.length() < projectNameMinimumLength ) {
                hasValidationErrors = Boolean.TRUE;
            }
            if ( projectName.length() > projectNameMaximumLength ) {
                hasValidationErrors = Boolean.TRUE;
            }
        }

        if ( projectDescription != null ) {
            if ( projectDescription.length() < projectDescriptionMinimumLength ) {
                hasValidationErrors = Boolean.TRUE;
            }
            if ( projectDescription.length() > projectDescriptionMaximumLength ) {
                hasValidationErrors = Boolean.TRUE;
            }
        }

        if ( projectStage != null ) {
            if ( projectStage.length() < projectStageMinimumLength ) {
                hasValidationErrors = Boolean.TRUE;
            }
            if ( projectStage.length() > projectStageMaximumLength ) {
                hasValidationErrors = Boolean.TRUE;
            }
        }

        if ( projectSizeArea != null ) {
            if ( projectSizeArea.length() < projectSizeAreaMinimumLength ) {
                hasValidationErrors = Boolean.TRUE;
            }
            if ( projectSizeArea.length() > projectSizeAreaMaximumLength ) {
                hasValidationErrors = Boolean.TRUE;
            }
        }

        if ( hasValidationErrors ) {
            return HttpStatus.UNPROCESSABLE_ENTITY;
        }

        builder.success( true );
        return HttpStatus.OK;
    }
}
