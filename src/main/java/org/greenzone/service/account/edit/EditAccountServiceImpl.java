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
package org.greenzone.service.account.edit;

import org.greenzone.domain.user.User;
import org.greenzone.helper.string.StringHelper;
import org.greenzone.repository.user.UserRepository;
import org.greenzone.service.account.edit.firstname.EditFirstNameRequest;
import org.greenzone.service.account.edit.firstname.EditFirstNameRequestValidator;
import org.greenzone.service.account.edit.firstname.EditFirstNameResponse;
import org.greenzone.service.account.edit.firstname.EditFirstNameResponse.EditFirstNameResponseBuilder;
import org.greenzone.service.account.edit.initialdata.AccountDetails;
import org.greenzone.service.account.edit.lastname.EditLastNameRequest;
import org.greenzone.service.account.edit.lastname.EditLastNameRequestValidator;
import org.greenzone.service.account.edit.lastname.EditLastNameResponse;
import org.greenzone.service.account.edit.lastname.EditLastNameResponse.EditLastNameResponseBuilder;
import org.greenzone.service.account.edit.password.EditPasswordRequest;
import org.greenzone.service.account.edit.password.EditPasswordRequestValidator;
import org.greenzone.service.account.edit.password.EditPasswordResponse;
import org.greenzone.service.account.edit.password.EditPasswordResponse.EditPasswordResponseBuilder;
import org.greenzone.service.account.edit.username.EditUserNameRequest;
import org.greenzone.service.account.edit.username.EditUserNameRequestValidator;
import org.greenzone.service.account.edit.username.EditUserNameResponse;
import org.greenzone.service.account.edit.username.EditUserNameResponse.EditUserNameResponseBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

/**
 * @author EN - Dec 17, 2024
 */
@Service
@RequiredArgsConstructor
public class EditAccountServiceImpl implements EditAccountService {

    private Logger logger = LoggerFactory.getLogger( EditAccountServiceImpl.class );

    private final StringHelper sh;
    private final EditUserNameRequestValidator editUsernameRequestValidator;
    private final EditFirstNameRequestValidator editFirstNameRequestValidator;
    private final EditLastNameRequestValidator editLastNameRequestValidator;
    private final EditPasswordRequestValidator passwordValidator;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Override
    public ResponseEntity<AccountDetails> getAccountDetails( User user ) {

        AccountDetails accountDetails =
                AccountDetails
                        .builder()
                        .firstName( user.getFirstName() )
                        .lastName( user.getLastName() )
                        .userId( user.getId() )
                        .username( user.getUsername() )
                        .build();

        logger.info( accountDetails.toString() );

        HttpStatus httpStatus = HttpStatus.OK;
        return ResponseEntity.status( httpStatus ).body( accountDetails );
    }


    @Override
    @Transactional
    public ResponseEntity<EditFirstNameResponse> editFirstName( User user,
            EditFirstNameRequest request ) {

        String firstName = sh.trimAndCapitaliseFirstLetter( request.getFirstName() );
        EditFirstNameResponseBuilder builder = EditFirstNameResponse.builder();
        HttpStatus httpStatus = editFirstNameRequestValidator.validate( builder, firstName );
        EditFirstNameResponse response = builder.build();

        if ( !response.getHasValidationErrors() ) {

            user.setFirstName( firstName );
            userRepository.save( user );
        }

        return ResponseEntity.status( httpStatus ).body( response );
    }


    @Override
    @Transactional
    public ResponseEntity<EditLastNameResponse> editLastName( User user,
            EditLastNameRequest request ) {

        String lastName = sh.trimAndCapitaliseFirstLetter( request.getLastName() );
        EditLastNameResponseBuilder builder = EditLastNameResponse.builder();
        HttpStatus httpStatus = editLastNameRequestValidator.validate( builder, lastName );
        EditLastNameResponse response = builder.build();

        if ( !response.getHasValidationErrors() ) {

            user.setLastName( lastName );
            userRepository.save( user );
        }

        return ResponseEntity.status( httpStatus ).body( response );
    }


    @Override
    @Transactional
    public ResponseEntity<EditUserNameResponse> editUsername( User user,
            EditUserNameRequest request ) {

        String username = sh.trim( request.getUsername() );
        EditUserNameResponseBuilder builder = EditUserNameResponse.builder();

        HttpStatus httpStatus = editUsernameRequestValidator.validate( builder, username );
        EditUserNameResponse response = builder.build();

        if ( !response.getHasValidationErrors() ) {

            user.setUsername( username );
            userRepository.save( user );
        }

        return ResponseEntity.status( httpStatus ).body( response );
    }



    @Override
    @Transactional
    public ResponseEntity<EditPasswordResponse> editPassword( User user,
            EditPasswordRequest request ) {

        String password = sh.trim( request.getPassword() );
        String passwordAlt = sh.trim( request.getPasswordAlt() );
        EditPasswordResponseBuilder builder = EditPasswordResponse.builder();
        HttpStatus httpStatus = passwordValidator.validate( builder, password, passwordAlt );
        EditPasswordResponse response = builder.build();

        if ( !response.getHasValidationErrors() ) {
            String passwordHash = passwordEncoder.encode( password );
            user.setPasswordHash( passwordHash );
            userRepository.save( user );
        }

        return ResponseEntity.status( httpStatus ).body( response );
    }
}
