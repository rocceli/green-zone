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
package org.greenzone.service.storage;

import java.io.File;
import java.io.IOException;

import org.greenzone.service.aws.AwsStorageService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author EN - Jan 14, 2025
 */
public class S3FileSystemProvider implements FileProvider {

    @Autowired
    private AwsStorageService awsStorageService;

    @Override
    public Boolean isS3FileSystemProvider() {

        return true;
    }


    @Override
    public String saveFile( File file, String directory ) {

        return awsStorageService.uploadFile( file );
    }


    @Override
    public void downloadFile( String pathOrKey, File destination ) throws IOException {

        awsStorageService.downloadFile( pathOrKey, destination );
    }


    @Override
    public File downloadFile( String pathOrKey ) {

        return awsStorageService.downloadFile( pathOrKey );
    }


    @Override
    public void deleteFile( String pathOrKey ) {

        awsStorageService.deleteFile( pathOrKey );
    }
}
