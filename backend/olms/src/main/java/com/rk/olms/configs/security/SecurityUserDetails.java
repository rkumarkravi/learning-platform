package com.rk.olms.configs.security;

import com.rk.olms.daos.models.UserEntity;
import lombok.Data;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@Data
public class SecurityUserDetails implements UserDetails {

    private final UserEntity userEntity;

    public SecurityUserDetails(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Implement logic to return authorities (roles) for the user
        return Collections.singleton(userEntity.getRole());
    }

    @Override
    public String getPassword() {
        return userEntity.getPassword();
    }

    @Override
    public String getUsername() {
        return userEntity.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // Implement your logic here
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // Implement your logic here
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Implement your logic here
    }

    @Override
    public boolean isEnabled() {
        return userEntity.isEnabled();
    }
}