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
package org.greenzone.helper.file;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.greenzone.helper.hash.HashHelper;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

/**
 * @author EN - Jan 14, 2025
 */
@Component
@RequiredArgsConstructor
public class FileHelperImpl implements FileHelper {

    private final HashHelper hashHelper;

    // @Autowired
    // private DirectoryHelper directoryHelper;

    private SimpleDateFormat sdf = new SimpleDateFormat( "yyyyMMdd_HH_mm_ss_SSS" );

    // This is used when FileProvider is the S3 one
    @Override
    public File getTempFile( String fileExtension ) {

        String javaIOTempPath = System.getProperty( "java.io.tmpdir" );
        File tempDirectoryFile = new File( javaIOTempPath );
        String timestamp = sdf.format( new Date() );

        String tempFileName =
                timestamp + "_" + hashHelper.getRandomHash() + "_" + fileExtension;

        File tempFile = new File( tempDirectoryFile, tempFileName );
        return tempFile;
    }


    @Override
    public Boolean delete( File file ) {

        try {
            Files.delete( Path.of( file.toURI() ) );
            return true;
        }
        catch ( IOException e ) {

            return false;
        }

    }
}
