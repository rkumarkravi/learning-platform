package com.rk.olms.services;

import com.rk.olms.configs.security.SecurityUserDetails;
import com.rk.olms.daos.models.UserEntity;
import com.rk.olms.daos.repos.UserRepository;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SecurityUserDetailService implements UserDetailsService {
    final UserRepository userRepository;

    public SecurityUserDetailService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public SecurityUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SecurityUserDetails securityUserDetails = null;
        Optional<UserEntity> userEntityOptional = userRepository.findByUsernameOrEmail(username);
        if (userEntityOptional.isPresent()) {
            securityUserDetails = new SecurityUserDetails(userEntityOptional.get());
        } else {
            throw new UsernameNotFoundException("Wrong Credentials!");
        }
        return securityUserDetails;
    }
}
