package com.example.application.config;

import com.example.application.user.UserService;
import com.example.application.user.dto.UserCredentialsDto;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private UserService userService;

    public CustomUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userService.findByUsername(username)
                .map(this::mapToUserDetails)
                .orElseThrow();
    }

    private UserDetails mapToUserDetails(UserCredentialsDto user) {
        return User.builder()
                .username(user.username())
                .password(user.password())
                .roles(user.roles().toArray(String[]::new))
                .build();
    }
}
