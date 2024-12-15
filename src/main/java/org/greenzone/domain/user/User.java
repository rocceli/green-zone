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
import java.util.List;
import java.util.Set;

import org.greenzone.domain.Domain;
import org.greenzone.domain.token.Token;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
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
@Table( name = "_user" )
public class User extends Domain {

    // When the user registers active is initially set to false.  It becomes true when the
    // user clicks on a link in an email.  Clicking the link validates that the user owns the 
    // email.
    //
    // If the register save finds that the email is already in an account that has active=false
    // then the record can be deleted before saving.  However if the account has active = true,
    // then this could mean the user has forgotten they already registered and they should be
    // prompted to go and click on forgotten password of the login page.
    @Column( name = "active", nullable = false )
    private Boolean active;

    // When the user registers active it is initially set to false.  It becomes active when the
    // user clicks on a link in an email. 
    //
    // Note the reason we have both active and enabled, is 
    // 
    // (i) the user can later close their account.  When this happens enabled is set to false
    //     and personal information is hashed out.
    //
    // (ii) An Admin can disable an account.  They may do this because the user has miss-behaved.
    @Column( name = "enabled", nullable = false )
    private Boolean enabled;

    @Column( name = "disabled_reason", nullable = true, unique = false, length = 256 )
    private String disabledReason;

    @Column( name = "first_name", nullable = false, unique = false, length = 32 )
    private String firstName;

    @Column( name = "last_name", nullable = false, unique = false, length = 32 )
    private String lastName;

    @Column( name = "email", nullable = false, unique = true, length = 255 )
    private String email;

    @Column( name = "username", nullable = false, unique = false, length = 32 )
    private String username;

    @Column( name = "phone", nullable = true, unique = false, length = 20 )
    private String phone;

    @Column( name = "avatarIdentifier", nullable = true, unique = false, length = 1024 )
    private String avatarIdentifier;

    @Column( name = "password_hash", nullable = true, unique = false, length = 256 )
    private String passwordHash;

    @Column( name = "email_activation_code", nullable = true, unique = false, length = 256 )
    private String emailActivationCode;

    @Column( name = "forgotten_password_code", nullable = true, unique = false, length = 256 )
    private String forgottenPasswordCode;

    @Builder.Default
    @ToString.Exclude
    @ManyToMany( fetch = FetchType.EAGER )
    @JoinTable( name = "user_role", joinColumns = @JoinColumn(
            name = "user_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey( name = "user_role_fk_user" ) ),
            inverseJoinColumns = @JoinColumn(
                    name = "role_id",
                    foreignKey = @ForeignKey( name = "user_role_fk_role" ),
                    referencedColumnName = "id" ) )
    private Set<Role> roles = new HashSet<>();

    @ToString.Exclude
    @OneToMany( mappedBy = "user" )
    private List<Token> tokens;
}
