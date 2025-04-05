package com.digiwardrobe.configurations;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.digiwardrobe.data_access.entity.UserEntity;
import com.digiwardrobe.data_access.repositories.UserRepository;

/*
 * As Spring Securuty didn't know that our user definition was, it sure as hell
 * doesn't know how to interact with an entity it doesn't know
 * 
 * After defining UserDetails for Spring Security, we now define a service to
 * act on Spring Security's behalf to make it all happen.
 * 
 * This converts our UserEntity into our UserDetails instance that is 
 * recognisable by Spring Security 
*/
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(final String email) throws UsernameNotFoundException {
        final UserEntity user = userRepository.findByEmail(email)
            .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return new CustomUserDetails(user);
    }
}
