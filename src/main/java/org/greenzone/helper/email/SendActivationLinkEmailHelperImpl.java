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
package org.greenzone.helper.email;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.greenzone.domain.user.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailPreparationException;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerConfigurationFactoryBean;

import freemarker.template.Template;

/**
 * @author EN - Dec 16, 2024
 */
@Component
public class SendActivationLinkEmailHelperImpl extends AbstractEmailHelper
        implements SendActivationLinkEmailHelper {

    private Logger logger = LoggerFactory.getLogger( SendActivationLinkEmailHelper.class );

    @Value( "${web.server.port}" )
    private int serverPort;

    @Value( "${web.server.protocol}" )
    private String protocol;

    @Value( "${web.server.domain}" )
    private String domain;

    @Value( "${company.email}" )
    private String email;

    @Value( "${company.phone}" )
    private String phone;

    @Value( "${company.address}" )
    private String address;

    @Value( "${company.website}" )
    private String website;

    @Value( "${company.company}" )
    private String company;

    public SendActivationLinkEmailHelperImpl(
            FreeMarkerConfigurationFactoryBean freeMarkerConfigurationFactoryBean ) {

        super( freeMarkerConfigurationFactoryBean );
    }


    private String buildActivationLink( String emailActivationCode ) {

        String url =
                protocol + "://" + domain + ":" + serverPort +
                        "/account/activate/validatecode/" + emailActivationCode;

        return url;
    }


    @Override
    public EmailSentResponse sendEmailActivationLink( User user ) {

        String templateName = "sendClientActivationLink.tfl";
        EmailSentResponse emailSentResponse;
        String subject = "Activation of GreenZone Account!";
        String emailActivationCode = user.getEmailActivationCode();

        String activationLink = buildActivationLink( emailActivationCode );
        logger.info( "activationLink: " + activationLink );

        String emailTo = user.getEmail();
        String firstName = user.getFirstName();
        List<File> attachments = new ArrayList<>();

        try {
            Template freeMarkerTemplate = this.configuration.getTemplate( templateName );

            Map<String, Object> model = new HashMap<String, Object>();
            model.put( "subject", subject );
            model.put( "activationLink", activationLink );
            model.put( "email", email );
            model.put( "phone", phone );
            model.put( "address", address );
            model.put( "website", website );
            model.put( "company", company );
            model.put( "firstName", firstName );
            File emailLogoFile = null;

            sendMessageInThread(
                    company, emailTo, model, freeMarkerTemplate, emailLogoFile,
                    attachments );

            emailSentResponse = new EmailSentResponse();
        }
        catch ( IOException e ) {

            emailSentResponse = new EmailSentResponse( e.getMessage() );

            throw new MailPreparationException(
                    "An I/O error occurred during composition " + "of email", e );
        }

        return emailSentResponse;
    }
}
