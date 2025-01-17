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

import java.util.List;

import org.greenzone.service.project.post.create.CreatePostResponse.CreatePostResponseBuilder;
import org.springframework.http.HttpStatus;

/**
 * @author EN - Jan 14, 2025
 */
public interface CreatePostRequestValidator {

    HttpStatus validate(
            CreatePostResponseBuilder builder,
            String postDescription,
            Long postProjectId,
            List<String> postImages,
            Long countryId );
}
