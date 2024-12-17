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
package org.greenzone.service.account.register;

import java.util.Set;

import org.greenzone.domain.user.Role;
import org.greenzone.domain.user.RoleGroup;
import org.greenzone.domain.user.RoleGroupType;
import org.greenzone.domain.user.User;
import org.greenzone.helper.hash.HashHelper;
import org.greenzone.helper.string.StringHelper;
import org.greenzone.repository.user.RoleGroupRepository;
import org.greenzone.repository.user.UserRepository;
import org.greenzone.service.account.register.RegisterResponse.RegisterResponseBuilder;
import org.greenzone.service.email.EmailService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

/**
 * @author EN - Dec 16, 2024
 */
@Service
@RequiredArgsConstructor
public class RegisterServiceImpl implements RegisterService {

    private final UserRepository userRepository;
    private final RoleGroupRepository roleGroupRepository;
    private final StringHelper sh;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    private final HashHelper hashHelper;
    private final RegisterRequestValidator registerRequestValidator;

    private void sendEmailActivation( User savedUser ) {

        emailService.sendActivationEmail( savedUser );
    }


    private boolean getEmailAlreadyRegistered( String email ) {

        return userRepository.findByEmail( email ).isPresent();
    }


    private boolean getUsernameAlreadyRegistered( String username ) {

        return userRepository.findByUsername( username ).isPresent();
    }


    private Set<Role> getRoles( RoleGroupType roleGroupEnum ) {

        RoleGroup roleGroup =
                roleGroupRepository.findById( roleGroupEnum.getId() ).get();

        return roleGroup.getRoles();
    }


    @Transactional
    @Override
    public ResponseEntity<RegisterResponse> register( RegisterRequest request ) {

        Set<Role> roles = getRoles( RoleGroupType.GROWER );
        String firstName = sh.trimAndCapitaliseFirstLetter( request.getFirstName() );
        String lastName = sh.trimAndCapitaliseFirstLetter( request.getLastName() );
        String email = sh.trimAndLowerCase( request.getEmail() );
        String username = sh.trimAndLowerCase( request.getUsername() );

        String password = sh.trim( request.getPassword() );
        String passwordAlt = sh.trim( request.getPasswordAlt() );

        RegisterResponseBuilder builder = RegisterResponse.builder();

        Boolean emailAlreadyRegistered = getEmailAlreadyRegistered( email );
        Boolean usernameAlreadyRegistered = getUsernameAlreadyRegistered( username );

        HttpStatus httpStatus =
                registerRequestValidator.validate(
                        builder,
                        emailAlreadyRegistered,
                        usernameAlreadyRegistered,
                        firstName,
                        lastName,
                        email,
                        username,
                        password,
                        passwordAlt );

        RegisterResponse response = builder.build();

        if ( !response.getHasValidationErrors() ) {

            User user = User.builder()
                    .firstName( firstName )
                    .lastName( lastName )
                    .email( email )
                    .passwordHash( passwordEncoder.encode( password ) )
                    .username( username )
                    .active( Boolean.FALSE ) // becomes true when have activated with email
                    .enabled( Boolean.FALSE )
                    // .address( address )
                    .build();

            user.getRoles().addAll( roles );
            user.setEmailActivationCode( hashHelper.getRandomHash() );
            User savedUser = userRepository.save( user );
            sendEmailActivation( savedUser );
        }

        return ResponseEntity.status( httpStatus ).body( response );
    }
}
