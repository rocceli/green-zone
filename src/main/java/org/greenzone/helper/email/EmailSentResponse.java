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


/**
 * @author EN - Dec 16, 2024
 */
public class EmailSentResponse {

    private boolean emailSent;
    private String error;

    public EmailSentResponse() {

        super();
        this.emailSent = true;
        this.error = null;
    }


    public EmailSentResponse( String error ) {

        super();
        this.emailSent = false;
        this.error = error;
    }


    public boolean isEmailSent() {

        return emailSent;
    }


    public void setEmailSent( boolean emailSent ) {

        this.emailSent = emailSent;
    }


    public String getError() {

        return error;
    }


    public void setError( String error ) {

        this.error = error;
    }
}
