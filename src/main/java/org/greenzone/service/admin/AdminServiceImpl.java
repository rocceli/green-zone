/*
    ================================================================================================
    This code is part of the Greenzone software.

    GreenZone is a closed source software of EN (Eliah Ngugi) which is owned by EN.
    
        Elijah Ngugi Gachuki
    
    This software is private closed source software.
    
    For further details look at or request greenzone-license.txt for further details.

    Copyright (C) 2024 

    Email:  elijah.ngugi.gachuki@gmail.com
    Domain: N/A

    ================================================================================================
    Author : EN
    ================================================================================================
 */
package org.greenzone.service.admin;

import java.util.List;
import java.util.Optional;

import org.greenzone.domain.admin.Admin;
import org.greenzone.domain.token.Token;
import org.greenzone.domain.user.User;
import org.greenzone.helper.string.StringHelper;
import org.greenzone.repository.admin.AdminRepository;
import org.greenzone.repository.token.TokenRepository;
import org.greenzone.repository.user.UserRepository;
import org.greenzone.service.admin.delete.DeleteAdminResponse;
import org.greenzone.service.admin.email.EditEmailRequest;
import org.greenzone.service.admin.email.EditEmailRequestValidator;
import org.greenzone.service.admin.email.EditEmailResponse;
import org.greenzone.service.admin.email.EditEmailResponse.EditEmailResponseBuilder;
import org.greenzone.service.admin.view.ViewAdminInitialData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

/**
 * @author EN - Dec 20, 2024
 */
@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private Logger logger = LoggerFactory.getLogger( AdminServiceImpl.class );

    // private final AddressRepository addressRepository;
    private final UserRepository userRepository;
    private final StringHelper sh;
    private final EditEmailRequestValidator editEmailRequestValidator;
    private final AdminRepository adminRepository;
    private final TokenRepository tokenRepository;

    @Override
    public ViewAdminInitialData getViewAdminInitialData( Long adminId ) {

        Admin admin = adminRepository.findById( adminId ).get();
        User user = admin.getUser();

        ViewAdminInitialData initialData =
                ViewAdminInitialData
                        .builder()
                        .active( user.getActive() )
                        .adminId( user.getId() )
                        .email( user.getEmail() )
                        .enabled( user.getEnabled() )
                        .firstName( user.getFirstName() )
                        .lastName( user.getLastName() )
                        .username( user.getUsername() )
                        .phone( user.getPhone() )
                        .build();

        return initialData;
    }


    @Override
    @Transactional
    public ResponseEntity<EditEmailResponse> editEmail( User user, EditEmailRequest request ) {

        String email = sh.trim( request.getEmail() );
        EditEmailResponseBuilder builder = EditEmailResponse.builder();
        Optional<User> optionalUser = userRepository.findByEmail( email );
        Boolean emailTaken = false;

        if ( optionalUser.isPresent() ) {
            User emailUser = optionalUser.get();

            if ( !emailUser.getId().equals( user.getId() ) ) {
                emailTaken = true;
            }
        }

        HttpStatus httpStatus = editEmailRequestValidator.validate( builder, email, emailTaken );
        EditEmailResponse response = builder.build();

        if ( !response.getHasValidationErrors() ) {

            user.setEmail( email );
            userRepository.save( user );
        }

        return ResponseEntity.status( httpStatus ).body( response );
    }


    @Transactional
    public DeleteAdminResponse _deleteAdmin( Admin admin ) {

        User user = admin.getUser();
        // Address address = user.getAddress();
        List<Token> tokens = user.getTokens();

        //        if ( address != null ) {
        //
        //            addressRepository.delete( address );
        //        }

        if ( tokens != null && tokens.size() > 0 ) {

            tokenRepository.deleteAll( tokens );
        }

        adminRepository.delete( admin );
        userRepository.delete( user );
        DeleteAdminResponse response = DeleteAdminResponse.builder().deleted( true ).build();
        return response;
    }


    @Transactional
    public DeleteAdminResponse _disableAdmin( Admin admin ) {

        User user = admin.getUser();
        user.setEnabled( false );
        userRepository.save( user );
        DeleteAdminResponse response = DeleteAdminResponse.builder().deleted( false ).build();
        return response;
    }


    @Override
    public ResponseEntity<DeleteAdminResponse> deleteAdmin( Long adminId ) {

        logger.info( "adminId: " + adminId );
        DeleteAdminResponse response = null;
        HttpStatus httpStatus = HttpStatus.OK;
        Optional<Admin> optionalAdmin = adminRepository.findById( adminId );

        if ( !optionalAdmin.isPresent() ) {

            httpStatus = HttpStatus.NOT_FOUND;
        }
        else {

            Admin admin = optionalAdmin.get();

            try {
                response = _deleteAdmin( admin );
            }
            catch ( Exception e ) {

                response = _disableAdmin( admin );
                httpStatus = HttpStatus.UNPROCESSABLE_ENTITY;
                logger.error( e.toString() );
                e.printStackTrace();
            }
        }

        return ResponseEntity.status( httpStatus ).body( response );
    }
}
