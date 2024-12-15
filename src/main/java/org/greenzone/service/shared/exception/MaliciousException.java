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
package org.greenzone.service.shared.exception;


/**
 * @author EN - Dec 16, 2024
 */
public class MaliciousException extends RuntimeException {

    private static final long serialVersionUID = -8376685694861111620L;

    public MaliciousException() {

    }


    public MaliciousException( String message ) {

        super( message );
    }


    public MaliciousException( Throwable cause ) {

        super( cause );
    }


    public MaliciousException( String message, Throwable cause ) {

        super( message, cause );
    }


    public MaliciousException( String message, Throwable cause, boolean enableSuppression,
            boolean writableStackTrace ) {

        super( message, cause, enableSuppression, writableStackTrace );
    }
}
