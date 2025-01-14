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
package org.greenzone.service.aws;

import java.io.File;
import java.io.IOException;

/**
 * @author EN - Jan 14, 2025
 */
public interface AwsStorageService {

    String uploadFile( File file );


    void deleteFile( String objectKey );


    void downloadFile( String objectKey, File destination ) throws IOException;


    File downloadFile( String path );
}
