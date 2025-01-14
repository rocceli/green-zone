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
import java.nio.file.Files;

/**
 * @author EN - Jan 14, 2025
 */
public class FileSystemFileProvider implements FileProvider {

    @Override
    public Boolean isS3FileSystemProvider() {

        return false;
    }


    @Override
    public String saveFile( File file, String directory ) {

        File destinationFile = new File( directory, file.getName() );

        try {

            File dir = new File( directory );
            if ( !dir.exists() ) {
                dir.mkdirs();
            }

            Files.copy( file.toPath(), destinationFile.toPath() );
            return destinationFile.getAbsolutePath();
        }
        catch ( IOException e ) {
            e.printStackTrace();
        }

        return destinationFile.getAbsolutePath();
    }


    @Override
    public File downloadFile( String pathOrKey ) {

        File file = null;

        if ( pathOrKey != null ) {

            file = new File( pathOrKey );
        }

        return file;
    }


    @Override
    public void downloadFile( String pathOrKey, File destinationFile ) throws IOException {

        File sourceFile = new File( pathOrKey );
        Files.copy( sourceFile.toPath(), destinationFile.toPath() );
    }


    @Override
    public void deleteFile( String pathOrKey ) {

        File file = new File( pathOrKey );

        if ( file.exists() ) {

            file.delete();
        }
    }
}
