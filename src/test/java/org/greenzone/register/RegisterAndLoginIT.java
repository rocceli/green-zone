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
package org.greenzone.register;

import java.net.URI;
import java.net.URISyntaxException;

import org.greenzone.domain.user.User;
import org.greenzone.helper.country.CountryTO;
import org.greenzone.repository.user.UserRepository;
import org.greenzone.service.account.register.RegisterRequest;
import org.greenzone.service.account.register.RegisterResponse;
import org.greenzone.service.login.email.LoginRequest;
import org.greenzone.service.login.email.LoginResponse;
import org.greenzone.service.register.activate.requestcode.RequestActivateCodeRequest;
import org.greenzone.service.register.activate.requestcode.RequestActivateCodeResponse;
import org.greenzone.service.register.activate.validatecode.ActivateValidateCodeRequest;
import org.greenzone.service.register.activate.validatecode.ActivateValidateCodeResponse;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.MethodMode;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.jdbc.SqlConfig.TransactionMode;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author EN - Feb 14, 2025
 */
@SpringBootTest( webEnvironment = WebEnvironment.RANDOM_PORT )
@ActiveProfiles( "integrationtest" )
public class RegisterAndLoginIT {

    private static final String FIRST_NAME = "John";
    private static final String LAST_NAME = "Doe";
    private static final String EMAIL = "john.doe@gmail.com";
    private static final String PASSWORD = "a123AB$#aaaaa";
    private static final String PASSWORDALT = "a123AB$#aaaaa";
    private static final String USERNAME = "artest";

    private Logger logger = LoggerFactory.getLogger( RegisterAndLoginIT.class );

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int randomServerPort;

    @Autowired
    private UserRepository userRepository;

    private void Register( CountryTO selectedCountry )
            throws URISyntaxException {

        final String baseUrl =
                "http://localhost:" + randomServerPort + "/api/v1/register/save";

        URI uri = new URI( baseUrl );

        RegisterRequest registerRequest =
                RegisterRequest.builder()
                        .firstName( FIRST_NAME )
                        .lastName( LAST_NAME )
                        .username( USERNAME )
                        .password( PASSWORD )
                        .passwordAlt( PASSWORDALT )
                        .email( EMAIL )
                        .build();

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<RegisterRequest> request = new HttpEntity<>( registerRequest, headers );

        ResponseEntity<RegisterResponse> result =
                this.restTemplate.postForEntity( uri, request, RegisterResponse.class );

        RegisterResponse response = result.getBody();
        logger.info( response.toString() );

        int statusCode = result.getStatusCode().value();
        logger.info( "statusCode: " + statusCode );
        Assert.assertEquals( 201, statusCode );

        Assert.assertNotNull( response );
        Assert.assertFalse( response.getHasValidationErrors() );
    }


    public void requestActivateCode( String email ) throws URISyntaxException {

        final String baseUrl =
                "http://localhost:" + randomServerPort + "/api/v1/account/activate/requestcode";

        URI uri = new URI( baseUrl );

        RequestActivateCodeRequest activateRequestCodeRequest =
                RequestActivateCodeRequest.builder().email( email ).build();

        HttpHeaders headers = new HttpHeaders();

        HttpEntity<RequestActivateCodeRequest> request =
                new HttpEntity<>( activateRequestCodeRequest, headers );

        ResponseEntity<RequestActivateCodeResponse> result =
                this.restTemplate.postForEntity( uri, request, RequestActivateCodeResponse.class );

        RequestActivateCodeResponse response = result.getBody();
        logger.info( "response: " + response );
        logger.info( "Response: " + response );
        logger.info( "Has validation errors: " + response.getHasValidationErrors() );
        logger.info( "Email not registered: " + response.getEmailNotRegistered() );
        logger.info( "Invalid email: " + response.getInvalidEmail() );
        Assert.assertNotNull( response );
        Assert.assertFalse( response.getHasValidationErrors() );
        Assert.assertFalse( response.getEmailNotRegistered() );
        Assert.assertFalse( response.getInvalidEmail() );

        int statusCode = result.getStatusCode().value();
        logger.info( "statusCode: " + statusCode );
        Assert.assertEquals( 200, statusCode );
    }


    private void activateUserUsingEmailActivationCode( String emailActivationCode )
            throws URISyntaxException {

        final String baseUrl =
                "http://localhost:" + randomServerPort + "/api/v1/account/activate/validatecode";

        URI uri = new URI( baseUrl );

        ActivateValidateCodeRequest activateValidateCodeRequest =
                ActivateValidateCodeRequest
                        .builder()
                        .emailActivationCode( emailActivationCode )
                        .build();

        HttpHeaders headers = new HttpHeaders();

        HttpEntity<ActivateValidateCodeRequest> request =
                new HttpEntity<>( activateValidateCodeRequest, headers );

        ResponseEntity<ActivateValidateCodeResponse> result =
                this.restTemplate.postForEntity( uri, request, ActivateValidateCodeResponse.class );

        ActivateValidateCodeResponse response = result.getBody();
        logger.info( "response: " + response );
        Assert.assertNotNull( response );

        String jwtToken = response.getJwtToken();
        Assert.assertNotNull( jwtToken );
    }


    private String login() throws URISyntaxException {

        final String baseUrl =
                "http://localhost:" + randomServerPort + "/api/v1/login";

        URI uri = new URI( baseUrl );

        LoginRequest loginRequest =
                LoginRequest.builder().email( EMAIL ).password( PASSWORD ).build();

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<LoginRequest> request = new HttpEntity<>( loginRequest, headers );

        ResponseEntity<LoginResponse> result =
                this.restTemplate.postForEntity( uri, request, LoginResponse.class );

        LoginResponse response = result.getBody();
        logger.info( "response: " + response );

        int statusCode = result.getStatusCode().value();
        logger.info( "statusCode: " + statusCode );
        Assert.assertEquals( 200, statusCode );

        Assert.assertNotNull( response );
        String token = response.getToken();
        Assert.assertNotNull( token );
        logger.info( "token: " + token );

        String[] roles = response.getRoles();
        Assert.assertTrue( roles.length == 1 );

        Assert.assertEquals( "GROWER", roles[0] );
        return token;
    }


    @Test
    @Sql( scripts = "classpath:sql/org/greenzone/account/register/registerAndLogin.sql",
            config = @SqlConfig( transactionMode = TransactionMode.ISOLATED ),
            executionPhase = ExecutionPhase.BEFORE_TEST_METHOD )
    @Transactional
    @Rollback
    @DirtiesContext( methodMode = MethodMode.AFTER_METHOD )
    void standardUserRegisterAndLoginTest() throws URISyntaxException {

        // Call API to set the emailActivationCode in the User.
        requestActivateCode( EMAIL );

        // Normally the emailActivationCode is sent in a link by email.  Here we are not
        // sending an email so insead we dig out the User and get the emailActivationCode.
        User user = userRepository.findByEmail( EMAIL ).get();
        String emailActivationCode = user.getEmailActivationCode();

        // We then  call the API to activate the User.  Usually this is API is called when the
        // user clicks on the emailActivatioNCode link placed in the email. It sets the user to
        // active.
        activateUserUsingEmailActivationCode( emailActivationCode );

        // Login as a standard user and retrieve the token.
        String famerToken = login();
        logger.info( "farmerToken: " + famerToken );
        logger.info( "Completed RegisterAndLoginIT" );
    }
}
