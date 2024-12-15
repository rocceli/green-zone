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
package org.greenzone.domain.user;


/**
 * @author EN - Dec 15, 2024
 */
public enum RoleType {

    GROWER( 1l, "GROWER" ),
    ADMIN( 2l, "ADMIN" ),
    SUPER_ADMIN( 3l, "SUPER_ADMIN" ),
    BUYER( 4l, "BUYER" ),
    TRANSPORTER( 5L, "TRANSPORTER" );

    private Long id;
    private String name;

    private RoleType( Long id, String name ) {

        this.id = id;
        this.name = name;
    }


    public Long getId() {

        return id;
    }


    public String getName() {

        return name;
    }
}
