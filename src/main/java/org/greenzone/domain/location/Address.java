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
package org.greenzone.domain.location;

import org.greenzone.domain.Domain;

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
 * @author EN - Dec 20, 2024
 */
@Data
@EqualsAndHashCode( callSuper = true, onlyExplicitlyIncluded = true )
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table( name = "_address" )
public class Address extends Domain {

    @Column( name = "line_1", nullable = true, unique = false, length = 32 )
    private String line1;

    @Column( name = "line_2", nullable = true, unique = false, length = 32 )
    private String line2;

    @Column( name = "line_3", nullable = true, unique = false, length = 32 )
    private String line3;

    @Column( name = "line_4", nullable = true, unique = false, length = 32 )
    private String line4;

    @Column( name = "town_city", nullable = true, unique = false, length = 32 )
    private String townCity;

    @Column( name = "zipPostcode", nullable = true, unique = false, length = 12 )
    private String zipPostcode;

    @ManyToOne( )
    @JoinColumn( name = "fk_country",
            foreignKey = @ForeignKey( name = "pledge_address_fk_country" ),
            nullable = false )
    private Country country;
}
