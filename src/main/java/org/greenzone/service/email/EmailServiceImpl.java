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
package org.greenzone.service.email;

import org.greenzone.domain.user.User;
import org.greenzone.helper.email.EmailSentResponse;
import org.greenzone.helper.email.SendActivationLinkEmailHelper;
import org.greenzone.helper.email.SendForgottenPasswordEmailHelper;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

/**
 * @author EN - Dec 16, 2024
 */
@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final SendActivationLinkEmailHelper sendActivationLinkEmailHelper;
    private final SendForgottenPasswordEmailHelper sendForgottenPasswordEmailHelper;

    @Override
    public EmailSentResponse sendActivationEmail( User user ) {

        EmailSentResponse emailSentResponse =
                sendActivationLinkEmailHelper.sendEmailActivationLink( user );

        return emailSentResponse;
    }


    @Override
    public EmailSentResponse sendForgottenPasswordEmail( User user ) {

        EmailSentResponse emailSentResponse =
                sendForgottenPasswordEmailHelper.sendForgottenPasswordEmail( user );

        return emailSentResponse;
    }
}
