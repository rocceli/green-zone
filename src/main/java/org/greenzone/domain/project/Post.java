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

import java.util.ArrayList;
import java.util.List;

import org.greenzone.domain.Domain;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
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
@Table( name = "_post" )
public class Post extends Domain {

    @Column( name = "description", nullable = true, unique = false, length = 4096 )
    private String description;

    @ElementCollection
    @CollectionTable( name = "post_images", joinColumns = @JoinColumn( name = "post_id" ) )
    @Column( name = "image_path" )
    private List<String> imagePaths = new ArrayList<>();

    @ManyToOne( )
    @JoinColumn( name = "fk_project",
            foreignKey = @ForeignKey( name = "_post_fk_project" ),
            nullable = false )
    private Project project;
}
