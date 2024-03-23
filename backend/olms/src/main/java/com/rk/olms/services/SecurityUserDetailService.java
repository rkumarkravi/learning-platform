package com.rk.olms.services;

import com.rk.olms.configs.security.SecurityUserDetails;
import com.rk.olms.daos.models.UserEntity;
import com.rk.olms.daos.repos.UserEntityRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SecurityUserDetailService implements UserDetailsService {
    final UserEntityRepository userEntityRepository;

    public SecurityUserDetailService(UserEntityRepository userEntityRepository) {
        this.userEntityRepository = userEntityRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SecurityUserDetails securityUserDetails=null;
        Optional<UserEntity> userEntityOptional =userEntityRepository.findByUsernameOrEmail(username);
        if (userEntityOptional.isPresent()) {
            securityUserDetails = new SecurityUserDetails(userEntityOptional.get());
        }
        return securityUserDetails;
    }
}
