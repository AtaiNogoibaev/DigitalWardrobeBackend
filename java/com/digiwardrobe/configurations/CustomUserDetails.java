package com.digiwardrobe.configurations;

import java.util.Collection;
import java.util.Collections;
import java.util.UUID;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import com.digiwardrobe.data_access.entity.UserEntity;

/*
 * Implementation of SpringSecurity UserDetails
 * 
 * Allows to describe user identifying properties, as Spring Security doesn't 
 * know what UserEntity in our db is we specify it here.
 * 
 * Also describes roles and permissions 
*/
public class CustomUserDetails implements UserDetails {

    private final UserEntity user;

    public CustomUserDetails( final UserEntity user) {
        this.user = user;
    }

    public UUID getUserId() {
        return user.getId();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // TODO Supposed to return "Authorities" or roles in simpler terms, empty for now
        return Collections.emptyList();
    }

    @Override
    public String getPassword() {
        return user.getHashedPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
