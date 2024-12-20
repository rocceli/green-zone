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
package org.greenzone.service.sadmins;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.greenzone.domain.admin.Admin;
import org.greenzone.domain.location.Address;
import org.greenzone.domain.location.Country;
import org.greenzone.domain.user.Role;
import org.greenzone.domain.user.RoleGroup;
import org.greenzone.domain.user.RoleGroupType;
import org.greenzone.domain.user.User;
import org.greenzone.helper.country.CountryHelper;
import org.greenzone.helper.country.CountryTO;
import org.greenzone.helper.string.StringHelper;
import org.greenzone.repository.admin.AdminRepository;
import org.greenzone.repository.location.AddressRepository;
import org.greenzone.repository.location.CountryRepository;
import org.greenzone.repository.user.RoleGroupRepository;
import org.greenzone.repository.user.UserRepository;
import org.greenzone.service.admins.create.CreateAdminInitialData;
import org.greenzone.service.admins.create.CreateAdminRequest;
import org.greenzone.service.admins.create.CreateAdminRequestValidator;
import org.greenzone.service.admins.create.CreateAdminResponse;
import org.greenzone.service.admins.create.CreateAdminResponse.CreateAdminResponseBuilder;
import org.greenzone.service.admins.view.AdminTO;
import org.greenzone.service.admins.view.ViewAdminsInitialData;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

/**
 * @author EN - Dec 20, 2024
 */
@Service
@RequiredArgsConstructor
public class AdminsServiceImpl implements AdminsService {

    private final UserRepository userRepository;
    private final CountryHelper countryHelper;
    private final CountryRepository countryRepository;
    private final AddressRepository addressRepository;
    private final StringHelper stringHelper;
    private final CreateAdminRequestValidator createAdminRequestValidator;
    private final PasswordEncoder passwordEncoder;
    private final RoleGroupRepository roleGroupRepository;
    private final AdminRepository adminRepository;

    private AdminTO createAdmin( Admin admin ) {

        User user = admin.getUser();

        AdminTO adminTO = AdminTO.builder()
                .active( user.getActive() )
                .enabled( user.getEnabled() )
                .adminId( admin.getId() )
                .firstName( user.getFirstName() )
                .lastName( user.getLastName() )
                .email( user.getEmail() )
                .phone( user.getPhone() )
                .build();

        return adminTO;
    }


    private AdminTO[] getAdmins( List<Admin> admins ) {

        List<AdminTO> adminTOs = new ArrayList<>();

        for ( Admin admin : admins ) {
            adminTOs.add( createAdmin( admin ) );
        }

        return adminTOs.toArray( new AdminTO[admins.size()] );

    }


    private boolean getEmailAlreadyRegistered( String email ) {

        return userRepository.findByEmail( email ).isPresent();
    }


    private boolean getUsernameAlreadyRegistered( String username ) {

        return userRepository.findByUsername( username ).isPresent();
    }


    private void saveAddress(
            User savedUser, String addressLine1, String addressLine2, String addressLine3,
            String addressLine4, String townCity, String zipPostcode, Long countryId ) {

        Country country = countryRepository.findById( countryId ).get();

        Address address =
                Address.builder()
                        .line1( addressLine1 )
                        .line2( addressLine2 )
                        .line3( addressLine3 )
                        .line4( addressLine4 )
                        .townCity( townCity )
                        .zipPostcode( zipPostcode )
                        .country( country )
                        .build();

        addressRepository.save( address );
    }


    @Override
    public ResponseEntity<CreateAdminInitialData> getCreateAdminInitialData() {

        CountryTO[] countries = countryHelper.getCountries();

        CreateAdminInitialData initialData =
                CreateAdminInitialData.builder().countries( countries ).build();

        return ResponseEntity.status( HttpStatus.OK ).body( initialData );
    }


    @Override
    public ViewAdminsInitialData getViewAdminsInitialData() {

        List<Admin> admins = adminRepository.findAll();
        AdminTO[] adminTOs = getAdmins( admins );
        return ViewAdminsInitialData.builder().admins( adminTOs ).build();
    }


    @Override
    @Transactional
    public ResponseEntity<CreateAdminResponse> createAdmin( CreateAdminRequest request ) {

        RoleGroupType roleGroupEnum = RoleGroupType.ADMIN;

        RoleGroup roleGroup =
                roleGroupRepository.findById( roleGroupEnum.getId() ).get();

        Set<Role> roles = roleGroup.getRoles();
        String firstName = stringHelper.trimAndCapitaliseFirstLetter( request.getFirstName() );
        String lastName = stringHelper.trimAndCapitaliseFirstLetter( request.getLastName() );
        String email = stringHelper.trimAndLowerCase( request.getEmail() );
        String username = stringHelper.trimAndLowerCase( request.getUsername() );
        String password = stringHelper.trim( request.getPassword() );
        String passwordAlt = stringHelper.trim( request.getPasswordAlt() );
        Long countryId = request.getCountryId();
        CreateAdminResponseBuilder builder = CreateAdminResponse.builder();

        Boolean emailAlreadyRegistered = getEmailAlreadyRegistered( email );
        Boolean usernameAlreadyRegistered = getUsernameAlreadyRegistered( username );

        HttpStatus httpStatus =
                createAdminRequestValidator.validate(
                        builder,
                        emailAlreadyRegistered,
                        usernameAlreadyRegistered,
                        firstName, lastName,
                        email, username, password,
                        passwordAlt, countryId );

        CreateAdminResponse response = builder.build();

        if ( !response.getHasValdationErrors() ) {

            // We activated account so admin does not need to receive an activation email
            User user = User.builder()
                    .firstName( firstName )
                    .lastName( lastName )
                    .email( email )
                    .passwordHash( passwordEncoder.encode( password ) )
                    .username( username )
                    .active( Boolean.TRUE )
                    .enabled( Boolean.FALSE )
                    .build();

            user.getRoles().addAll( roles );
            User savedUser = userRepository.save( user );
            saveAddress( savedUser, null, null, null, null, null, null, countryId );
            Admin admin = Admin.builder().user( savedUser ).build();
            adminRepository.save( admin );

            builder.adminId( admin.getId() );
            response = builder.build();
        }

        return ResponseEntity.status( httpStatus ).body( response );
    }
}
