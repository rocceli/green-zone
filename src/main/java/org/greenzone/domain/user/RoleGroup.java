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

import java.util.HashSet;
import java.util.Set;

import org.greenzone.domain.Domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

/**
 * @author EN - Dec 15, 2024
 */
@Data
@EqualsAndHashCode( callSuper = true, onlyExplicitlyIncluded = true )
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table( name = "role_group" )
public class RoleGroup extends Domain {

    @Column( name = "name", nullable = false, unique = false, length = 60 )
    private String name;

    @Column( name = "description", nullable = true, unique = false,
            length = 120 )
    private String description;

    @Builder.Default
    @ToString.Exclude
    @ManyToMany( fetch = FetchType.EAGER, targetEntity = Role.class, cascade = {
            CascadeType.PERSIST, CascadeType.MERGE } )
    @JoinTable( name = "rolegroup_role", joinColumns = { @JoinColumn(
            name = "rolegroup_id",
            foreignKey = @ForeignKey( name = "rolegroup_role_fk_rolegroup" ),
            referencedColumnName = "id" ) },
            inverseJoinColumns = { @JoinColumn(
                    name = "role_id",
                    foreignKey = @ForeignKey( name = "rolegroup_role_fk_role" ),
                    referencedColumnName = "id" ) } )
    private Set<Role> roles = new HashSet<Role>();
}
