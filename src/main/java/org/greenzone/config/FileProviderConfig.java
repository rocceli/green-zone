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
package org.greenzone.config;

import org.greenzone.service.storage.FileProvider;
import org.greenzone.service.storage.FileProviderEnum;
import org.greenzone.service.storage.FileSystemFileProvider;
import org.greenzone.service.storage.S3FileSystemProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author EN - Jan 14, 2025
 */
@Configuration
public class FileProviderConfig {

    @Value( "${file.provider}" )
    private String fileProvider;

    @Bean
    FileProvider createFileProvider() {

        FileProviderEnum fileProviderEnum = FileProviderEnum.valueOf( fileProvider );
        FileProvider fileProvider = null;

        switch ( fileProviderEnum ) {

            case FILE_SYSTEM_FILE_PROVIDER:
                fileProvider = new FileSystemFileProvider();
                break;

            case S3_FILE_PROVIDER:
                fileProvider = new S3FileSystemProvider();
                break;
        }

        return fileProvider;
    }
}
