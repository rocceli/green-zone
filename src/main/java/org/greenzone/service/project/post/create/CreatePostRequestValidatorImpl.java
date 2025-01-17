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
package org.greenzone.service.project.post.create;

import java.util.Base64;
import java.util.List;

import org.greenzone.service.project.post.create.CreatePostResponse.CreatePostResponseBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

/**
 * @author EN - Jan 14, 2025
 */
@Component
@RequiredArgsConstructor
public class CreatePostRequestValidatorImpl implements CreatePostRequestValidator {

    @Value( "${file.max-image-size}" )
    private long maxImageSize;

    @Value( "${project-description.validation.minimum-length}" )
    private Integer postDescriptionMinimumLength;

    @Value( "${project-description.validation.maximum-length}" )
    private Integer postDescriptionMaximumLength;

    @Override
    public HttpStatus validate( CreatePostResponseBuilder builder, String postDescription,
            Long postProjectId, List<String> postImages, Long countryId ) {

        Boolean hasValidationErrors = Boolean.FALSE;

        Boolean postDescriptionTooShort = Boolean.FALSE;
        Boolean postDescriptionTooLong = Boolean.FALSE;

        Boolean postImageTooBig = Boolean.FALSE;

        HttpStatus httpStatus = HttpStatus.CREATED;

        if ( postDescription == null || postDescription
                .length() < postDescriptionMinimumLength ) {

            System.out.print( postDescription.length() );
            postDescriptionTooShort = Boolean.TRUE;
            hasValidationErrors = Boolean.TRUE;
        }

        if ( postDescription != null && postDescription
                .length() > postDescriptionMaximumLength ) {

            postDescriptionTooLong = Boolean.TRUE;
            hasValidationErrors = Boolean.TRUE;
        }

        if ( postImages != null ) {
            for ( String base64Image : postImages ) {
                String[] parts = base64Image.split( "," );
                byte[] imageBytes = Base64.getDecoder().decode( parts[1] );

                if ( imageBytes.length > maxImageSize ) {
                    postImageTooBig = true;
                    hasValidationErrors = true;
                }
            }
        }

        if ( hasValidationErrors ) {

            httpStatus = HttpStatus.UNPROCESSABLE_ENTITY;
        }

        builder
                .hasValidationErrors( hasValidationErrors )
                .postDescriptionTooLong( postDescriptionTooLong )
                .postDescriptionTooShort( postDescriptionTooShort )
                .postImageTooBig( postImageTooBig )
                .build();

        return httpStatus;
    }

}
