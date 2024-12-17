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
import java.util.List;
import java.util.Map;

import org.greenzone.helper.hash.HashHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.mail.MailPreparationException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.ui.freemarker.FreeMarkerConfigurationFactoryBean;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

/**
 * @author EN - Dec 16, 2024
 */
public class AbstractEmailHelper {

    private Logger logger = LoggerFactory.getLogger( AbstractEmailHelper.class );

    @Autowired
    protected JavaMailSender javaMailSender;

    protected Configuration configuration;

    @Autowired
    protected MessageSource resourceBundleMessageSource;

    @Autowired
    protected HashHelper hashHelper;

    @Value( "${email.do-not-reply}" )
    private String fromEmail;

    @Value( "${email.disabled}" )
    protected String emailDisabled;

    @Value( "${email.do-not-reply}" )
    protected String emailDoNotReplyTo;

    @Value( "${web.server.domain}" )
    protected String hostName;

    // Autowired in constructor
    protected FreeMarkerConfigurationFactoryBean freeMarkerConfigurationFactoryBean;

    protected AbstractEmailHelper(
            FreeMarkerConfigurationFactoryBean freeMarkerConfigurationFactoryBean ) {

        this.freeMarkerConfigurationFactoryBean =
                freeMarkerConfigurationFactoryBean;

        try {

            if ( freeMarkerConfigurationFactoryBean != null ) {

                this.configuration = freeMarkerConfigurationFactoryBean.createConfiguration();
            }
        }
        catch ( TemplateException | IOException e ) {

            throw new MailPreparationException(
                    "A FreeMarker error occurred creating configuration", e );
        }
    }


    private void addAttachements( List<File> attachments, MimeMessageHelper helper ) {

        if ( attachments != null ) {
            if ( attachments.size() > 0 ) {
                for ( File attachment : attachments ) {
                    if ( attachment.exists() ) {
                        try {
                            helper.addAttachment( attachment.getName(), attachment );
                        }
                        catch ( MessagingException e ) {
                            throw new RuntimeException( "Failed to attachments", e );
                        }
                    }
                }
            }
        }
    }


    private void sendMessage(
            String company,
            String emailTo,
            final Map<String, Object> model,
            String fromEmail,
            final Template freemarkerTemplate,
            File emailLogoFile,
            List<File> attachments ) {

        if ( emailDisabled != null && "true".equals( emailDisabled ) ) {

            logger.debug( "Email not sent because email sending disabled "
                    + "with property email.disabled" );

            return;
        }

        if ( emailTo.indexOf( "@" ) == -1 ) {

            logger.debug( "Email not sent because email is a test email and has no @ in it" );

            return;
        }

        javaMailSender.send( new MimeMessagePreparator() {

            @Override
            public void prepare( MimeMessage mimeMessage ) throws MessagingException {

                try {
                    MimeMessageHelper helper = new MimeMessageHelper( mimeMessage, true, "UTF-8" );
                    helper.setSubject( ( String )model.get( "subject" ) );
                    helper.setFrom( fromEmail, company );
                    helper.setTo( emailTo );
                    model.put( "img", "none" );

                    String text =
                            FreeMarkerTemplateUtils.processTemplateIntoString(
                                    freemarkerTemplate, model );

                    helper.setText( text, true );

                    if ( emailLogoFile != null ) {
                        if ( emailLogoFile.exists() ) {
                            helper.addInline( "logoemail01", emailLogoFile );
                        }
                    }

                    addAttachements( attachments, helper );
                }
                catch ( IOException e ) {

                    throw new MailPreparationException(
                            "An I/O error occurred during composition "
                                    + "of email", e );
                }
                catch ( TemplateException e ) {

                    throw new MailPreparationException(
                            "A FreeMarker error occurred during "
                                    + "composition of email", e );
                }
            }
        } );
    }


    protected void sendMessageInThread(
            String company,
            String emailTo,
            final Map<String, Object> model,
            final Template freemarkerTemplate,
            File emailLogoFile,
            List<File> attachments ) {

        String fromEmail = emailDoNotReplyTo;

        logger.debug( "Spinning off sending email in seperate thread using "
                + "freemarker template :" + freemarkerTemplate.getName() );

        Runnable runnable = new Runnable() {

            @Override
            public void run() {

                try {
                    sendMessage( company, emailTo, model, fromEmail, freemarkerTemplate,
                            emailLogoFile, attachments );
                }
                catch ( Exception e ) {

                    logger.error(
                            "Error sending email using freemarker template, "
                                    + freemarkerTemplate.getName(), e );

                    throw new RuntimeException(
                            "Error sending email using freemarker template", e );
                }
            }
        };

        Thread thread = new Thread( runnable );
        thread.start();
    }
}
