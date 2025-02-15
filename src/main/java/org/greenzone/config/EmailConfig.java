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
package org.greenzone.config;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

/**
 * @author EN - Dec 16, 2024
 */
@Configuration
public class EmailConfig {

    @Value( "${email.do-not-reply}" )
    private String emailFrom;

    @Value( "${email.host}" )
    private String host;

    @Value( "${email.port}" )
    private int port;

    @Value( "${email.protocol}" )
    private String protocol;

    @Value( "${email.username}" )
    private String username;

    @Value( "${email.password}" )
    private String password;

    @Bean
    public SimpleMailMessage doNotReplyTemplateMessage() {

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom( emailFrom );
        return simpleMailMessage;
    }


    @Bean
    public JavaMailSender javaMailSender() {

        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setHost( host );
        javaMailSender.setPort( port );
        javaMailSender.setProtocol( protocol );
        javaMailSender.setUsername( username );
        javaMailSender.setPassword( password );

        Properties javaMailProperties = new Properties();
        javaMailProperties.setProperty( "mail.transport.protocol", "smtp" );
        javaMailProperties.setProperty( "mail.smtp.auth", "true" );
        javaMailProperties.setProperty( "mail.smtp.starttls.enable", "true" );
        javaMailProperties.setProperty( "mail.debug", "true" );
        javaMailProperties.setProperty( "mail.smtp.from", emailFrom );
        // javaMailProperties.setProperty( "mail.smtp.ssl.trust", host );

        javaMailSender.setJavaMailProperties( javaMailProperties );
        return javaMailSender;
    }
}
