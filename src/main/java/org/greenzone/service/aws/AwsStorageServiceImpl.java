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
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import org.apache.commons.io.FilenameUtils;
import org.greenzone.helper.file.FileHelper;
import org.greenzone.helper.hash.HashHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;

import jakarta.annotation.PostConstruct;

/**
 * @author EN - Jan 14, 2025
 */
public class AwsStorageServiceImpl implements AwsStorageService {

    @Autowired
    private HashHelper hashHelper;

    @Autowired
    private FileHelper fileHelper;

    @Value( "${aws.bucket-name}" )
    private String bucketName;

    @Value( "${aws.access-key}" )
    private String accessKey;

    @Value( "${aws.secret-key}" )
    private String secretKey;

    private AmazonS3 s3client;

    private String generateObjectKey( String filePath ) {

        String fileName = FilenameUtils.getBaseName( filePath );
        String extension = FilenameUtils.getExtension( filePath );
        String hash = hashHelper.getRandomHash();
        String generatedFileName = fileName + "_" + hash + "." + extension;
        return generatedFileName;
    }


    @PostConstruct
    private void initializeAmazon() {

        AWSCredentials credentials = new BasicAWSCredentials( this.accessKey, this.secretKey );

        s3client = AmazonS3ClientBuilder
                .standard()
                .withCredentials( new AWSStaticCredentialsProvider( credentials ) )
                .withRegion( Regions.EU_WEST_2 )
                .build();

    }


    @Override
    public String uploadFile( File file ) {

        try {
            String objectKey = generateObjectKey( file.getAbsolutePath() );

            PutObjectRequest putObjectRequest =
                    new PutObjectRequest( bucketName, objectKey, file ).withCannedAcl(
                            CannedAccessControlList.Private );

            s3client.putObject( putObjectRequest );
            return objectKey;
        }
        catch ( AmazonServiceException e ) {

            throw new RuntimeException( "Could not upload file: " + e.getMessage() );
        }
    }


    @Override
    public void deleteFile( String objectKey ) {

        try {
            s3client.deleteObject( bucketName, objectKey );
        }
        catch ( AmazonServiceException e ) {

            throw new RuntimeException( "Could not delete file: " + e.getMessage(), e );
        }
    }


    @Override
    public void downloadFile( String objectKey, File destination ) {

        S3Object s3object = null;

        try {

            s3object = s3client.getObject( bucketName, objectKey );
            S3ObjectInputStream inputStream = s3object.getObjectContent();
            Files.copy( inputStream, destination.toPath(), StandardCopyOption.REPLACE_EXISTING );
        }
        catch ( Exception e ) {

            throw new RuntimeException( "Error downloading the file: " + e.getMessage(), e );
        }
        finally {

            if ( s3object != null ) {

                try {
                    s3object.close();
                }
                catch ( IOException e ) {

                    // do nothing
                }
            }
        }
    }


    @Override
    public File downloadFile( String objectKey ) {

        String fileExtension = FilenameUtils.getExtension( objectKey );
        File file = fileHelper.getTempFile( fileExtension );
        downloadFile( objectKey, file );
        return file;
    }
}
