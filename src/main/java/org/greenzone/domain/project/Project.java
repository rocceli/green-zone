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
package org.greenzone.domain.project;

import org.greenzone.domain.Domain;
import org.greenzone.domain.location.Address;
import org.greenzone.domain.user.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * @author EN - Dec 27, 2024
 */
@Data
@EqualsAndHashCode( callSuper = true, onlyExplicitlyIncluded = true )
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table( name = "_project" )
public class Project extends Domain {

    @Column( name = "stage", nullable = false, unique = false, length = 32 )
    private String stage;

    @Column( name = "description", nullable = true, unique = false, length = 4096 )
    private String description;

    @Column( name = "size_area", nullable = true, unique = false, length = 32 )
    private String sizeArea;

    @ManyToOne( )
    @JoinColumn( name = "fk_address",
            foreignKey = @ForeignKey( name = "_project_fk_address" ),
            nullable = false )
    private Address address;

    @ManyToOne( )
    @JoinColumn( name = "fk_user",
            foreignKey = @ForeignKey( name = "_project_fk_user" ),
            nullable = false )
    private User user;
}
