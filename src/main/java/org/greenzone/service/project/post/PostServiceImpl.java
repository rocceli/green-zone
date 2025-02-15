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
package org.greenzone.service.project.post;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

import org.greenzone.domain.project.Post;
import org.greenzone.domain.project.Project;
import org.greenzone.domain.user.User;
import org.greenzone.helper.string.StringHelper;
import org.greenzone.repository.project.PostRepository;
import org.greenzone.repository.project.ProjectRepository;
import org.greenzone.service.project.post.create.CreatePostRequest;
import org.greenzone.service.project.post.create.CreatePostRequestValidator;
import org.greenzone.service.project.post.create.CreatePostResponse;
import org.greenzone.service.project.post.create.CreatePostResponse.CreatePostResponseBuilder;
import org.greenzone.service.storage.FileProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

/**
 * @author EN - Jan 14, 2025
 */
@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final StringHelper sh;
    private final CreatePostRequestValidator createPostRequestValidator;
    private final ProjectRepository projectRepository;
    private final PostRepository postRepository;
    private final FileProvider fileProvider;

    @Value( "${file.upload-post-dir}" )
    private String uploadPostDir;

    @Override
    @Transactional
    public ResponseEntity<CreatePostResponse> createPost( CreatePostRequest request, User user,
            Long projectId ) {

        String description = sh.trimAndCapitaliseFirstLetter( request.getPostDescription() );
        Long projectPostId = projectId;
        List<String> uploadImages = request.getPostImages();

        Long tempId = GenerateId();

        List<Project> projects = projectRepository.findByUserEnabledTrueAndUserActiveTrueAndUserId(
                user.getId() );

        Project project = findProjectById( projects, projectPostId );

        List<String> imagePaths = new ArrayList<>();

        CreatePostResponseBuilder builder = CreatePostResponse.builder();

        HttpStatus httpStatus = createPostRequestValidator.validate( builder, description,
                projectPostId, uploadImages, projectPostId );

        CreatePostResponse response = builder.build();

        if ( !response.getHasValidationErrors() ) {
            String uploadPostImageDir = uploadPostDir + File.separator + tempId;

            for ( String base64Image : uploadImages ) {
                File tempFile = null;
                try {
                    // Decode the Base64 string
                    String[] parts = base64Image.split( "," );
                    byte[] imageBytes = Base64.getDecoder().decode( parts[1] );

                    // Create a temporary file
                    tempFile = File.createTempFile( "upload_", ".tmp" );
                    try ( FileOutputStream fos = new FileOutputStream( tempFile )) {
                        fos.write( imageBytes );
                    }

                    String imagePath = fileProvider.saveFile( tempFile, uploadPostImageDir );
                    imagePaths.add( imagePath );
                }
                catch ( IOException e ) {
                    e.printStackTrace();
                }
                finally {
                    if ( tempFile != null && tempFile.exists() ) {
                        tempFile.delete();
                    }
                }
            }
            Post post = Post.builder()
                    .description( description )
                    .project( project )
                    .imagePaths( imagePaths )
                    .build();

            postRepository.save( post );
            httpStatus = HttpStatus.CREATED;
        }
        response = builder.build();

        return ResponseEntity.status( httpStatus ).body( response );
    }


    //============================Used Within create post===============================
    private Project findProjectById( List<Project> projects, Long projectId ) {

        for ( Project project : projects ) {
            if ( project.getId().equals( projectId ) ) {
                return project;
            }
        }
        return null;
    }


    private Long GenerateId() {

        UUID uuid = UUID.randomUUID();
        Long tempId = uuid.getMostSignificantBits();
        while ( postRepository.existsById( tempId ) ) {
            uuid = UUID.randomUUID();
            tempId = uuid.getMostSignificantBits();
        }
        return tempId;
    }
    //===================================================================================

}
